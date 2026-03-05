package com.axiptv.android.data.api

import com.axiptv.android.domain.model.DeviceStatusResponse
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class DeviceStatusRequest(
    val device_hash: String
)

interface DeviceStatusApi {
    @POST("api/device/status")
    suspend fun getDeviceStatus(@Body request: DeviceStatusRequest): Response<DeviceStatusResponse>
}
