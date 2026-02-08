package com.example.midi2link

import android.media.midi.MidiDeviceService
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiReceiver

class VirtualMidiDeviceService : MidiDeviceService() {
    private val midiReceiver: MidiReceiver = MyMidiReceiver()
    private val inputPortReceivers = arrayOf(midiReceiver)

    override fun onGetInputPortReceivers() = inputPortReceivers

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        MidiDataRepository.inputPortOpen.postValue(status.isInputPortOpen(0))
    }

}

class MyMidiReceiver : MidiReceiver() {

    override fun onSend(
        msg: ByteArray?,
        offset: Int,
        count: Int,
        timestamp: Long
    ) {
        val midiInfo = "Msg: ${msg}, Offset: ${offset}, Count: ${count}, Timestamp: ${timestamp}"
        MidiDataRepository.lastMessage.postValue(midiInfo) // Safe to use postValue
    }
}
