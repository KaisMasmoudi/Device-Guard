package com.example.deviceguard.utils

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.text.TextUtils

fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService>): Boolean {
    val serviceId = "${context.packageName}/${service.name}"
    val enabledServices = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
    return enabledServices?.let {
        TextUtils.SimpleStringSplitter(':').run {
            setString(it)
            while (hasNext()) {
                if (next().equals(serviceId, ignoreCase = true)) {
                    true
                }
            }
            false
        }
    } ?: false
}