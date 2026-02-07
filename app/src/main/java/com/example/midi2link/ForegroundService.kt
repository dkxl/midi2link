package com.example.midi2link

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.jvm.java

class ForegroundService : Service() {

    private val channelId = "Midi2LinkServiceChannel"

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> start()
            Action.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this,
            channelId
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("MIDI 2 Link")
            .setContentText("Running")
            .build()
        startForeground(1, notification)
    }

    enum class Action {
        START, STOP
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(channelId,
            "MIDI 2 Link Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

}