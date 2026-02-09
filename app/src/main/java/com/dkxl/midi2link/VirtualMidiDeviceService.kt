package com.dkxl.midi2link

import android.media.midi.MidiDeviceService
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiReceiver
import com.mobileer.miditools.MidiFramer

class VirtualMidiDeviceService : MidiDeviceService() {
    /*
     * Prepare the chain of message handlers:
     *  Raw input comes to this service's midiReceiver
     *    - midiReceiver sends raw data to a MidiFramer
     *    - midiFramer assembled into complete messages and sends to a MidiDataParser
     *    - midiDataParser converts to String and sends to the MidiDataRepository
     *  UI elements can observe data changes in the DataRepository
     */



    private val midiReceiver: MidiReceiver = MyMidiReceiver()
    private val midiDataParser: MidiDataParser = MidiDataParser(MidiDataRepository.lastMessage)
    private val midiFramer: MidiFramer = MidiFramer(midiDataParser)
    private val inputPortReceivers = arrayOf(midiReceiver)

    override fun onGetInputPortReceivers() = inputPortReceivers

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        MidiDataRepository.inputPortOpen.postValue(status.isInputPortOpen(0))
    }

    inner class MyMidiReceiver : MidiReceiver() {
        override fun onSend(msg: ByteArray?, offset: Int, count: Int, timestamp: Long) {
            // send raw data to be assembled into messages and added to the MidiDataRepository
            midiFramer.send(msg, offset, count, timestamp)
        }
    }

}
