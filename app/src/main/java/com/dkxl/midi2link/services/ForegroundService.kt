package com.dkxl.midi2link.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.dkxl.midi2link.R
import com.dkxl.midi2link.link.LinkRepository
import com.dkxl.midi2link.link.LinkWrapper
import com.dkxl.midi2link.midi.MidiRepository
import kotlin.jvm.java


/*
 * Observes the MidiRepository and pushes BPM and Start/Stop changes to Ableton Link peers
 */
class ForegroundService(
    private val abletonLink: LinkWrapper = LinkWrapper(),
    private val midiData: MidiRepository = MidiRepository
) : Service() {

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

    override fun onDestroy() {
        abletonLink.setEnabled(false)
        unsubscribeFromLiveData()
        super.onDestroy()
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, channelId
        )
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("MIDI 2 Link")
            .setContentText("Running")
            .build()
        startForeground(1, notification)
        // how to handle failures?
        subscribeToLiveData()
        abletonLink.create(enable = true)
    }

    enum class Action {
        START, STOP
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            channelId,
            "MIDI 2 Link Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun subscribeToLiveData() {
        midiData.midiBpm.observeForever {
            // tell Link peers the new BPM when MIDI clock's bpm changes
            abletonLink.setTempo(it)
        }
        midiData.midiIsPlaying.observeForever {
            // tell Link peers to start/stop when MIDI says start/stop
            abletonLink.setPlaying(it)
        }
    }

    private fun unsubscribeFromLiveData() {
        midiData.midiBpm.removeObserver {  }
        midiData.midiIsPlaying.removeObserver {  }
    }


}