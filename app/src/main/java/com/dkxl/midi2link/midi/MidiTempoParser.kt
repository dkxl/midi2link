package com.dkxl.midi2link.midi

import android.media.midi.MidiReceiver
import com.mobileer.miditools.MidiConstants

/*
 * A MidiReceiver that calculates current song tempo and song start/stop state,
 * and posts any changes to the Data Repo
 * Assumes the MIDI data has been assembled into complete messages by a MidiFramer
 */
class MidiTempoParser(private val dataRepo: MidiDataRepository ) : MidiReceiver() {

    private val startTime: Long = System.nanoTime()
    private var clockDeque = ArrayDeque<Long>(CLOCK_DEQUE_CAPACITY)

    override fun onSend(
        data: ByteArray?,
        offset: Int,
        count: Int,
        timestamp: Long
    ) {
        // only interested in single byte system control messages
        if (data != null && count == 1) {

            when (data[offset]) {

                MidiConstants.STATUS_TIMING_CLOCK -> {
                    // do something
                }

                MidiConstants.STATUS_START -> {
                    dataRepo.pieceStarted.postValue(true)
                }

                MidiConstants.STATUS_CONTINUE -> {
                    dataRepo.pieceStarted.postValue(true)
                }

                MidiConstants.STATUS_STOP -> {
                    dataRepo.pieceStarted.postValue(false)
                }

            }

        }

    }
}