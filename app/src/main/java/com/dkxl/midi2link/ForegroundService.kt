package com.dkxl.midi2link

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.dkxl.midi2link.ableton.Link
import com.dkxl.midi2link.midi.MidiDataRepository
import kotlin.jvm.java

class ForegroundService() : Service() {

    private val channelId = "Midi2LinkServiceChannel"
    private val data = MidiDataRepository

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

    override fun onDestroy() {
        Link.setEnabled(false)
        unsubscribeFromLiveData()
        super.onDestroy()
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this,
            channelId
        )
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("MIDI 2 Link")
            .setContentText("Running")
            .build()
        startForeground(1, notification)
        // how to handle failures?
        subscribeToLiveData()
        Link.create()
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

    private fun subscribeToLiveData() {
        data.midiBpm.observeForever {
            // tell Live Data peers the new BPM
            Link.setTempo(it)
        }
        data.midiIsPlaying.observeForever {
            Link.setPlaying(it)
        }
    }

    private fun unsubscribeFromLiveData() {
        data.midiBpm.removeObserver {  }
        data.midiIsPlaying.removeObserver {  }
    }


}