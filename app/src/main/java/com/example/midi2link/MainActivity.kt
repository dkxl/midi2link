package com.example.midi2link

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                MainScreen(modifier)
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
            MainScreen(modifier)
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

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
//    onServiceConnected: () -> Unit,
//    onServiceDisconnecting: () -> Unit,
//    onServiceError: () -> Unit,
) {
    var service: VirtualMidiDeviceService? by remember { mutableStateOf(null) }

    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val binderHelper = binder as? VirtualMidiDeviceService.BinderHelper
                service = binderHelper?.getService()
             //   onServiceConnected()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                service = null
              //  onServiceError()
            }
        }
    }

    val context = LocalContext.current

    DisposableEffect(key1 = Unit) {
        val intent = Intent(context, VirtualMidiDeviceService::class.java)

        val bound = context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        if (!bound) {
         //   onServiceError()
        }

        onDispose {
            context.unbindService(connection)
           // onServiceDisconnecting()
        }
    }

    Text("Midi2Link Virtual MIDI Device")

    if (service != null) {
        Text(service!!.deviceInfo.toString())

        Text("Receiver Started: ${service!!.isInputPortOpen()}")

    } else {
        Text("unable to bind")
    }


}