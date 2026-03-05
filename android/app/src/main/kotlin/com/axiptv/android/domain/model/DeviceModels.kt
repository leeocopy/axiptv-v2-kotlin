package com.axiptv.android.domain.model

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
data class DeviceStatusResponse(
    val allowed: Boolean,
    val reason: DeviceStatusReason,
    val trial_end_at: Instant,
    val active_until: Instant?,
    val server_time: Instant
)
