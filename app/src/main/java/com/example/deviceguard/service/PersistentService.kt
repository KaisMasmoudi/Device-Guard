package com.example.deviceguard.service

import android.accessibilityservice.AccessibilityService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.example.deviceguard.R
import com.example.deviceguard.utils.isAccessibilityServiceEnabled

class PersistentService : Service() {

    companion object {
        const val CHANNEL_ID = "DeviceGuardChannel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "DeviceGuard Notification Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("DeviceGuard Needs Accessibility Service")
        .setContentText("Please enable the DeviceGuard Accessibility Service for full protection.")
        .setSmallIcon(R.drawable.ic_notification)
        .setOngoing(true)
        .build()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isAccessibilityServiceEnabled(this, AppMonitorService::class.java)) {
            // Continuously prompt user to enable the Accessibility Service
            val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}