package com.example.deviceguard

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deviceguard.Receiver.MyDeviceAdminReceiver
import com.example.deviceguard.ui.theme.DeviceGuardTheme

//this class is not used

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_ENABLE_ADMIN = 1
    }

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Device Policy Manager and ComponentName
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)

        // Set the content using Jetpack Compose
        setContent {
            DeviceGuardTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = ::activateDeviceAdmin) {
                            Text(text = "Activate Admin")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = ::openAccessibilitySettings) {
                            Text(text = "Open Accessibility Settings")
                        }
                    }
                    /*AdminActivateButton(
                        onClick = {
                            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                                putExtra(
                                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                    "Admin permission explanation."
                                )
                            }
                            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)
                        }
                    )*/
                }
            }
        }
    }

    private fun activateDeviceAdmin() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
            putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Admin permission is required to enable protective features."
            )
        }
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)
    }

    private fun openAccessibilitySettings() {
        val intent = Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                // Admin enabled
            } else {
                // Admin not enabled
            }
        }
    }

    @Composable
    fun AdminActivateButton(onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = onClick) {
                Text(text = "Activate Admin")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AdminActivateButtonPreview() {
        DeviceGuardTheme {
            AdminActivateButton(onClick = {})
        }
    }
}


