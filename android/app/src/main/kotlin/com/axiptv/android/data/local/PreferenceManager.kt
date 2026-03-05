package com.axiptv.android.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.axiptv.android.domain.model.DeviceStatusResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "settings")

class PreferenceManager @Inject constructor(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private val LAST_CHECK_MS = longPreferencesKey("last_check_ms")
        private val DEVICE_STATUS = stringPreferencesKey("device_status")
    }

    val lastCheckMs: Flow<Long> = context.dataStore.data.map { it[LAST_CHECK_MS] ?: 0L }
    
    val deviceStatus: Flow<DeviceStatusResponse?> = context.dataStore.data.map { preferences ->
        preferences[DEVICE_STATUS]?.let { json.decodeFromString<DeviceStatusResponse>(it) }
    }

    suspend fun saveDeviceStatus(status: DeviceStatusResponse) {
        context.dataStore.edit { preferences ->
            preferences[DEVICE_STATUS] = json.encodeToString(status)
            preferences[LAST_CHECK_MS] = System.currentTimeMillis()
        }
    }
}
