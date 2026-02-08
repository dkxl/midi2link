package com.example.midi2link

import android.content.Intent
import android.media.midi.MidiDeviceService
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiReceiver
import android.os.Binder
import android.os.IBinder
import android.util.Log

class VirtualMidiDeviceService : MidiDeviceService() {
    private var receiverStarted = false
    private val midiReceiver: MidiReceiver = MyMidiReceiver()
    private val inputPortReceivers = arrayOf(midiReceiver)
    private val binder = BinderHelper()

//    override fun onBind(intent: Intent?): IBinder? {
//        super.onBind(intent)
//        return binder
//    }

    override fun onGetInputPortReceivers() = inputPortReceivers

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        if (status.isInputPortOpen(0) && !receiverStarted) {
            receiverStarted = true
        } else if (!status.isInputPortOpen(0) && receiverStarted) {
            receiverStarted = false
        }
    }

    fun isInputPortOpen(): Boolean {
        return receiverStarted
    }

    inner class BinderHelper : Binder() {
        fun getService(): VirtualMidiDeviceService = this@VirtualMidiDeviceService
    }


}

class MyMidiReceiver : MidiReceiver() {

    override fun onSend(
        msg: ByteArray?,
        offset: Int,
        count: Int,
        timestamp: Long
    ) {
        //TODO: put the message in some sort of buffer
        Log.e("midi2link", "Rx: $msg")
    }
}