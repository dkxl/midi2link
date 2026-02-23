package com.dkxl.midi2link.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.dkxl.midi2link.services.ForegroundService


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
fun ServiceControlScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // TODO remember state of the service, and show/hide buttons accordingly.

    Text( "MIDI Input", fontWeight = FontWeight.Bold)

    Button(onClick = {
        val action = ForegroundService.Action.START.toString()
        manageForegroundService(context, action)
    }) {
        Text(text = "Start MIDI Service")
    }
    Button(onClick = {
        val action = ForegroundService.Action.STOP.toString()
        manageForegroundService(context, action)
    }) {
        Text(text = "Stop MIDI Service")
    }
}


private fun manageForegroundService(context: Context, action: String) {
    with(context) {
        val serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.action = action
        startService(serviceIntent)
    }
}
