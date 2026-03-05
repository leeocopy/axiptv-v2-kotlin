package com.axiptv.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axiptv.android.ui.activation.ActivatePurchaseScreen
import com.axiptv.android.ui.activation.ActivationOverlayScreen
import com.axiptv.android.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.runtime.CompositionLocalProvider
import com.axiptv.android.util.DeviceType
import com.axiptv.android.util.LocalDeviceType
import com.axiptv.android.util.rememberDeviceType

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val deviceType = rememberDeviceType()
            AxIptvTheme {
                CompositionLocalProvider(LocalDeviceType provides deviceType) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToActivation = {
                    navController.navigate("activation") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("main") {
            // Placeholder for Main Dashboard
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                androidx.compose.material3.Text("Main Dashboard - Active", color = Color.White)
            }
        }
        composable("activation") {
            ActivatePurchaseScreen()
        }
    }
}

@Composable
fun AxIptvTheme(content: @Composable () -> Unit) {
    androidx.compose.material3.MaterialTheme(
        colorScheme = androidx.compose.material3.darkColorScheme(),
        content = content
    )
}
