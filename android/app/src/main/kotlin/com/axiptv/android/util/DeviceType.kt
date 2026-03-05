package com.axiptv.android.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration

enum class DeviceType {
    TV, MOBILE
}

val LocalDeviceType = compositionLocalOf { DeviceType.MOBILE }

@Composable
fun rememberDeviceType(): DeviceType {
    val configuration = LocalConfiguration.current
    // Heuristic: TV usually has a large screen and is often identified via UI mode or simply by being a "TV" product.
    // Here we use screen width/height as a fallback for generic TV boxes.
    val isTv = configuration.screenWidthDp >= 960 || configuration.smallestScreenWidthDp >= 600
    // Check for TV UI mode
    val uiModeManager = androidx.compose.ui.platform.LocalContext.current.getSystemService(android.content.Context.UI_MODE_SERVICE) as android.app.UiModeManager
    val isTvMode = uiModeManager.currentModeType == android.content.res.Configuration.UI_MODE_TYPE_TELEVISION
    
    return if (isTv || isTvMode) DeviceType.TV else DeviceType.MOBILE
}
