package com.example.deviceguard.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class AppMonitorService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName.toString()
            Log.d("AppMonitorService", "App opened: $packageName")

            // Check if the current screen is a settings screen
            if (isSettingsScreen(event.source)) {
                restrictSettingsAccess()
            }
        }
    }

    override fun onInterrupt() {
        // Handle interruptions if needed
    }

    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
            packageNames = null // Null means monitor all apps
        }
        this.serviceInfo = info
        super.onServiceConnected()
    }

    private fun isSettingsScreen(nodeInfo: AccessibilityNodeInfo?): Boolean {
        nodeInfo ?: return false

        // Check for specific UI elements that indicate a settings screen
        return nodeInfo.className == "android.widget.FrameLayout" &&
                nodeInfo.contentDescription?.contains("Settings") == true
    }

    private fun restrictSettingsAccess() {
        // Implement logic to restrict access to settings
        // This could involve closing the settings screen or displaying a message
        Log.d("AppMonitorService", "Restricting settings access")

        // Example: Return the user to the home screen
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(homeIntent)
    }
}