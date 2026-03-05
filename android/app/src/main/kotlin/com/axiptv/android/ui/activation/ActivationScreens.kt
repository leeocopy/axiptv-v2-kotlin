package com.axiptv.android.ui.activation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import com.axiptv.android.util.DeviceType
import com.axiptv.android.util.LocalDeviceType

@Composable
fun ActivationOverlayScreen() {
    val deviceType = LocalDeviceType.current
    val fontSize = if (deviceType == DeviceType.TV) 32.sp else 20.sp
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Your Trial has Expired", color = Color.White, fontSize = fontSize)
    }
}

@Composable
fun ActivatePurchaseScreen() {
    val deviceType = LocalDeviceType.current
    val fontSize = if (deviceType == DeviceType.TV) 32.sp else 20.sp

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Scan QR to Activate (${deviceType.name})", color = Color.White, fontSize = fontSize)
    }
}
