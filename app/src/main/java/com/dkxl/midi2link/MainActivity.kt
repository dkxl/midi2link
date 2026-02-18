package com.dkxl.midi2link

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.dkxl.midi2link.midi.MidiDataRepository
import com.dkxl.midi2link.ui.theme.Midi2LinkTheme
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

        LinkWrapper.create()

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

    companion object {
        init {
            System.loadLibrary("midi2link")
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



/*
0

I asked myself the same question recently, and big thanks to Larry Schiefer’s comment for giving me the idea that checking if a service is running is the wrong approach.

"It is different, but not unworkable. The typical pattern is for your app (e.g. Activity) to always start your Service and the service knows its own state. If it is already running, it can simply ignore the new start request (or use it to stimulate an update, if needed.) As long as this is not happening constantly, the overhead involved with calling startService should be negligible.



If you have a running Foreground Service and want to get an instance of it, you don’t check all running services or try to get an instance from Android (that was my first thought too). Instead, you just start your Foreground Service and bind to it anytime you start/resume your main activity.

Here’s how:

1. Start and bind to the service in your Activity

val intent = Intent(this, YourForegroundService::class.java)
startForegroundService(intent)
bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
This makes sure the service is always running and bound when your app is active.

2. Handle service state inside onStartCommand

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    ...
    isServiceRunning = true
    return START_STICKY
}
This way, you don’t start your Foreground Service twice.

With this approach, you will always have one instance of your Foreground Service along with a bind to call your service methods. Hope that helps!
 */


@Composable
fun Midi2LinkControls(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // TODO remember state of the service, and show/hide buttons accordingly.

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
    // TODO: Move to a ViewModel
    // TODO: use rememberSaveable

    var inputPortOpen: Boolean by remember { mutableStateOf(false) }

    var songBpm: Int by remember { mutableStateOf(0) }

    var bpmValid: Boolean by remember { mutableStateOf(false) }

    var songStarted: Boolean by remember { mutableStateOf(false) }

    var tempo by remember { mutableDoubleStateOf(0.0)  }
    var phase by remember { mutableDoubleStateOf(0.0)  }


    MidiDataRepository.inputPortOpen.observe(lifecycleOwner) { state ->
        inputPortOpen = state
    }

    MidiDataRepository.songBpm.observe(lifecycleOwner) { msg ->
        songBpm = msg
    }

    MidiDataRepository.bpmValid.observe(lifecycleOwner) {state ->
        bpmValid = state
    }

    MidiDataRepository.songStarted.observe(lifecycleOwner) {state ->
        songStarted = state
    }

    // TODO: is there a jetpack object to display boolean states
    Text( "Input Port Connected:", fontWeight = FontWeight.Bold)
    Text( if (inputPortOpen) "Yes" else "No")

    Text("Beats Per Minute:", fontWeight = FontWeight.Bold)
    Text(songBpm.toString())

    Text( "BPM Valid:", fontWeight = FontWeight.Bold)
    Text( if (bpmValid) "Yes" else "No")

    Text( "Song Started:", fontWeight = FontWeight.Bold)
    Text( if (songStarted) "Yes" else "No")

    AbletonLinkScreen(
        isRunning = songStarted,
        curTempo = tempo,
        curPhase = phase,
        onTempoRefresh = { tempo = it },
        onPhaseRefresh = { phase = it },
        onRunStateChange = { songStarted = it },
    )



}

@Composable
private fun AbletonLinkScreen(
    isRunning: Boolean,
    curTempo: Double,
    curPhase: Double,
    onTempoRefresh: (Double) -> Unit,
    onPhaseRefresh: (Double) -> Unit,
    onRunStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp )
    )
    {

        Button(
            onClick= {
                val tempo = LinkWrapper.getTempo()
                onTempoRefresh(tempo) },
        ) {
            "Refresh Song Tempo"
        }
        Text("Current Tempo: $curTempo")

        Button(
            onClick= {
                val phase = LinkWrapper.getPhase()
                onPhaseRefresh(phase)
            },
        ) {
            "Refresh Song Phase"
        }
        Text("Current Phase: $curPhase")


        Button(
            onClick = {
                onRunStateChange(!isRunning)
                LinkWrapper.setEnable()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(if (isRunning) "Stop" else "Start")
        }

    }

}
