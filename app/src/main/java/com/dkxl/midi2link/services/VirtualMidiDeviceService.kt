package com.dkxl.midi2link.services

import android.media.midi.MidiDeviceService
import android.media.midi.MidiDeviceStatus
import android.media.midi.MidiReceiver
import com.dkxl.midi2link.midi.MidiRepository
import com.dkxl.midi2link.midi.MidiTempoParser
import com.mobileer.miditools.MidiFramer
import java.io.IOException

class VirtualMidiDeviceService : MidiDeviceService() {
    /*
     * Prepare the chain of message handlers:
     *  Raw input comes to this service's midiReceiver
     *    - midiReceiver sends raw data to a MidiFramer
     *    - midiFramer assembled into complete messages and sends to a MidiDataParser
     *    - midiTempoParser extracts BPM and song state and sends to the MidiDataRepository
     *  UI elements can observe data changes in the DataRepository
     */

    private val midiReceiver: MidiReceiver = MyMidiReceiver()

    private val midiTempoParser: MidiTempoParser = MidiTempoParser(MidiRepository)

    private val midiFramer: MidiFramer = MidiFramer(midiTempoParser)
    private val inputPortReceivers = arrayOf(midiReceiver)

    override fun onGetInputPortReceivers() = inputPortReceivers

    override fun onDeviceStatusChanged(status: MidiDeviceStatus) {
        MidiRepository.midiInputIsConnected.postValue(status.isInputPortOpen(0))
    }

    inner class MyMidiReceiver : MidiReceiver() {
        override fun onSend(data: ByteArray?, offset: Int, count: Int, timestamp: Long) {
            // Use the timestamp when we received the message
            val myTimestamp : Long = System.nanoTime()
            try {
                // send raw data to be assembled into messages and added to the MidiDataRepository
                midiFramer.send(data, offset, count, myTimestamp)
            } catch (e: IOException) {
                MidiRepository.lastMidiMessageReceived.postValue(e.toString())
            }
        }
    }

}