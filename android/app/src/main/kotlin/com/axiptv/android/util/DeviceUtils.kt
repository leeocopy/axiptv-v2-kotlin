package com.axiptv.android.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.security.MessageDigest

object DeviceUtils {

    @SuppressLint("HardwareIds")
    fun getDeviceHash(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "fallback"
        val salt = "AXIPTV_SALT_2026"
        val input = androidId + salt
        
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray())
        
        return hashBytes.joinToString("") { "%02x".format(it) }.take(16)
    }
}
