package com.axiptv.backend

import kotlinx.datetime.Instant
import kotlin.test.*

class DeviceDecisionTest {

    private val service = DeviceService()
    private val now = Instant.parse("2026-03-05T10:00:00Z")
    private val trialFuture = Instant.parse("2026-03-12T10:00:00Z")
    private val past = Instant.parse("2026-03-01T10:00:00Z")

    @Test
    fun `new device should be in trial`() {
        val device = Device(
            id = 1,
            deviceHash = "test",
            firstSeenAt = now,
            lastSeenAt = now,
            trialEndAt = trialFuture,
            isActive = false,
            activeUntil = null
        )

        val result = service.calculateDecision(device, now)

        assertTrue(result.allowed)
        assertEquals(DeviceStatusReason.TRIAL_ACTIVE, result.reason)
    }

    @Test
    fun `expired trial device should be blocked`() {
        val device = Device(
            id = 1,
            deviceHash = "test",
            firstSeenAt = past,
            lastSeenAt = past,
            trialEndAt = past, // Trial ended in the past
            isActive = false,
            activeUntil = null
        )

        val result = service.calculateDecision(device, now)

        assertFalse(result.allowed)
        assertEquals(DeviceStatusReason.TRIAL_EXPIRED, result.reason)
    }

    @Test
    fun `active device should be allowed`() {
        val device = Device(
            id = 1,
            deviceHash = "test",
            firstSeenAt = past,
            lastSeenAt = past,
            trialEndAt = past,
            isActive = true,
            activeUntil = trialFuture // Active until future
        )

        val result = service.calculateDecision(device, now)

        assertTrue(result.allowed)
        assertEquals(DeviceStatusReason.ACTIVE, result.reason)
    }

    @Test
    fun `active but expired license should be blocked if trial also expired`() {
        val device = Device(
            id = 1,
            deviceHash = "test",
            firstSeenAt = past,
            lastSeenAt = past,
            trialEndAt = past,
            isActive = true,
            activeUntil = past // License expired
        )

        val result = service.calculateDecision(device, now)

        assertFalse(result.allowed)
        assertEquals(DeviceStatusReason.ACTIVE_EXPIRED, result.reason)
    }
}
