package com.example.midi2link

import android.media.midi.MidiDeviceService
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiReceiver
import android.util.Log


class VirtualMidiDeviceService : MidiDeviceService() {

    private var receiverStarted = false
    private val midiReceiver: MidiReceiver = MyMidiReceiver()
    private val inputPortReceivers = arrayOf(midiReceiver)

    override fun onGetInputPortReceivers() = inputPortReceivers

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        if (status.isInputPortOpen(0) && !receiverStarted) {
            Log.e("midi2link", "Rx: Started")
            receiverStarted = true
        } else if (!status.isInputPortOpen(0) && receiverStarted) {
            Log.e("midi2link", "Rx: Stopped")
            receiverStarted = false
        }
    }

}

class MyMidiReceiver : MidiReceiver() {

    override fun onSend(
        msg: ByteArray?,
        offset: Int,
        count: Int,
        timestamp: Long
    ) {
        Log.e("midi2link", "Rx: $msg")
    }
}