package com.axiptv.backend

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class DeviceService {

    fun resolveStatus(device_hash: String): DeviceStatusResponse {
        val now = Clock.System.now()
        
        return transaction {
            val deviceRow = DevicesTable.select { DevicesTable.deviceHash eq device_hash }.singleOrNull()
            
            val device = if (deviceRow == null) {
                val trialEnd = now.plus(7, DateTimeUnit.DAY)
                val id = DevicesTable.insertAndGetId {
                    it[deviceHash] = device_hash
                    it[firstSeenAt] = now
                    it[lastSeenAt] = now
                    it[trialEndAt] = trialEnd
                    it[isActive] = false
                    it[activeUntil] = null
                }
                Device(id.value, device_hash, now, now, trialEnd, false, null)
            } else {
                DevicesTable.update({ DevicesTable.deviceHash eq device_hash }) {
                    it[lastSeenAt] = now
                }
                Device(
                    deviceRow[DevicesTable.id],
                    deviceRow[DevicesTable.deviceHash],
                    deviceRow[DevicesTable.firstSeenAt],
                    now,
                    deviceRow[DevicesTable.trialEndAt],
                    deviceRow[DevicesTable.isActive],
                    deviceRow[DevicesTable.activeUntil]
                )
            }

            calculateDecision(device, now)
        }
    }

    /**
     * Decision Logic - Pure function for unit testing
     */
    fun calculateDecision(device: Device, now: Instant): DeviceStatusResponse {
        val activeNow = device.isActive && (device.activeUntil == null || device.activeUntil > now)
        val trialNow = now < device.trialEndAt
        
        val allowed = activeNow || trialNow
        
        val reason = when {
            activeNow -> DeviceStatusReason.ACTIVE
            device.isActive && device.activeUntil != null && device.activeUntil <= now -> DeviceStatusReason.ACTIVE_EXPIRED
            trialNow -> DeviceStatusReason.TRIAL_ACTIVE
            else -> DeviceStatusReason.TRIAL_EXPIRED
        }

        return DeviceStatusResponse(
            allowed = allowed,
            reason = reason,
            trial_end_at = device.trialEndAt,
            active_until = device.activeUntil,
            server_time = now
        )
    }
}
