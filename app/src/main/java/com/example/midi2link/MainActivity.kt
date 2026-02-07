package com.example.midi2link

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.midi2link.ui.theme.Midi2LinkTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


private fun manageForegroundService(context: Context, action: String) {
    with(context) {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.action = action
        startService(serviceIntent)
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Midi2LinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold {
                        title = "MIDI 2 Link"
                        Midi2LinkMain(Modifier.padding(it))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Midi2LinkMain(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionState = rememberPermissionState(
                Manifest.permission.POST_NOTIFICATIONS
            )

            if (notificationPermissionState.status.isGranted) {
                Midi2LinkControls(modifier)
            } else {
                val textToShow = if (notificationPermissionState.status.shouldShowRationale) {
                    "Notification permission is required so this app can continue to run in the background" +
                            " Please grant the permission."
                } else {
                    "Notification permission is required so this app can continue to run in the background." +
                            " Please grant the permission"
                }
                Text(textToShow)
                Button(onClick = { notificationPermissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        } else {
            // Notification permissions not required until TIRAMISU
            Midi2LinkControls(modifier)
        }
    }
}


@Composable
fun Midi2LinkControls(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Button(onClick = {
        val action = ForegroundService.Action.START.toString()
        manageForegroundService(context, action)
    }) {
        Text(text = "Start Service")
    }
    Button(onClick = {
        val action = ForegroundService.Action.STOP.toString()
        manageForegroundService(context, action)
    }) {
        Text(text = "Stop Service")
    }
}

