package com.example.deviceguard.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.deviceguard.service.DeviceGuardService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            val serviceIntent = Intent(context, DeviceGuardService::class.java)
            context?.startForegroundService(serviceIntent)
        }
    }
}

/*
<fdroid>
<repository>
<name>Your Private Repo Name</name>
<url>https://fdroid.leadwire.io/fdroid/repo/</url>
<fingerprint>YOUR_REPO_FINGERPRINT</fingerprint>
</repository>
</fdroid>
        */