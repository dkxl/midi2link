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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
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
        val lifecycleOwner = this

        enableEdgeToEdge()
        setContent {
            Midi2LinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold {
                        title = "MIDI 2 Link"
                        Midi2LinkMain(lifecycleOwner, Modifier.padding(it))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Midi2LinkMain(
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp ),

    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionState = rememberPermissionState(
                Manifest.permission.POST_NOTIFICATIONS
            )

            if (notificationPermissionState.status.isGranted) {
                Midi2LinkControls(modifier)
                MainScreen(lifecycleOwner, modifier)
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
            MainScreen(lifecycleOwner, modifier)
        }
    }
}


@Composable
fun Midi2LinkControls(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    /* TODO remember state of the service, and show/hide buttons accordingly.
        Or publish to LiveDataRepository (but not the same one used for MIDI device)
    */

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
fun MainScreen(lifecycleOwner: LifecycleOwner, modifier: Modifier = Modifier){

    var lastMessage: String by remember { mutableStateOf("None") }
    var inputPortOpen: Boolean by remember { mutableStateOf(false) }

    MidiDataRepository.lastMessage.observe(lifecycleOwner) { msg ->
        lastMessage = msg
    }

    MidiDataRepository.inputPortOpen.observe(lifecycleOwner) { state ->
        inputPortOpen = state
    }

    Text( "Input Port Connected:", fontWeight = FontWeight.Bold)
    Text( "$inputPortOpen")


    Text("Last MIDI Message:", fontWeight = FontWeight.Bold)

    Text(lastMessage)


}