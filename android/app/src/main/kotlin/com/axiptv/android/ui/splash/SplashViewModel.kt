package com.axiptv.android.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axiptv.android.data.api.DeviceStatusApi
import com.axiptv.android.data.api.DeviceStatusRequest
import com.axiptv.android.data.local.PreferenceManager
import com.axiptv.android.domain.model.DeviceStatusResponse
import com.axiptv.android.util.DeviceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashState {
    object Loading : SplashState()
    object Authorized : SplashState()
    object Unauthorized : SplashState()
    data class Error(val message: String) : SplashState()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val api: DeviceStatusApi,
    private val preferences: PreferenceManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state: StateFlow<SplashState> = _state

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch {
            val deviceHash = DeviceUtils.getDeviceHash(context)
            val cachedStatus = preferences.deviceStatus.first()
            
            try {
                val response = api.getDeviceStatus(DeviceStatusRequest(deviceHash))
                if (response.isSuccessful && response.body() != null) {
                    val status = response.body()!!
                    preferences.saveDeviceStatus(status)
                    handleDecision(status)
                } else {
                    handleOffline(cachedStatus)
                }
            } catch (e: Exception) {
                handleOffline(cachedStatus)
            }
        }
    }

    private fun handleDecision(status: DeviceStatusResponse) {
        if (status.allowed) {
            _state.value = SplashState.Authorized
        } else {
            _state.value = SplashState.Unauthorized
        }
    }

    private fun handleOffline(cachedStatus: DeviceStatusResponse?) {
        if (cachedStatus != null) {
            // FIX_MODE: Never deny first launch offline if cached trial exists
            if (cachedStatus.allowed) {
                _state.value = SplashState.Authorized
            } else {
                _state.value = SplashState.Unauthorized
            }
        } else {
            // No cache, truly offline first time
            _state.value = SplashState.Error("Offline and no activation data found.")
        }
    }
}
