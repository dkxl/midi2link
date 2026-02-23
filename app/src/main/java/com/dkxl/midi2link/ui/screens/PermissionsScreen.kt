package com.dkxl.midi2link.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dkxl.midi2link.midi.MidiViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen(
    midiViewModel: MidiViewModel,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp ),

        ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionsState = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.INTERNET
                )
            )

            if (permissionsState.allPermissionsGranted) {
                ServiceControlScreen(modifier)
                MainScreen(midiViewModel, modifier)
            } else {
                Text("Notification permission is required so this app can continue to run in the background")
                Text("Internet permission is required so this app can communicate with Ableton Link devices")
                Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                    Text("Request permissions")
                }
            }
        } else {
            // Notification permissions not required until TIRAMISU
            ServiceControlScreen(modifier)
            MainScreen(midiViewModel, modifier)
        }
    }
}
