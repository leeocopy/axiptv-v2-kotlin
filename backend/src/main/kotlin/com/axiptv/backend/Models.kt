package com.axiptv.backend

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
enum class DeviceStatusReason {
    ACTIVE,
    ACTIVE_EXPIRED,
    TRIAL_ACTIVE,
    TRIAL_EXPIRED
}

@Serializable
data class DeviceStatusRequest(
    val device_hash: String
)

@Serializable
data class DeviceStatusResponse(
    val allowed: Boolean,
    val reason: DeviceStatusReason,
    val trial_end_at: Instant,
    val active_until: Instant?,
    val server_time: Instant
)

data class Device(
    val id: Long,
    val deviceHash: String,
    val firstSeenAt: Instant,
    val lastSeenAt: Instant,
    val trialEndAt: Instant,
    val isActive: Boolean,
    val activeUntil: Instant?
)
