package com.dkxl.midi2link.midi

import android.media.midi.MidiReceiver
import com.mobileer.miditools.MidiConstants

/*
 * A MidiReceiver that calculates current song tempo and song start/stop state,
 * and posts any changes to the Data Repo
 * Assumes the MIDI data has been assembled into complete messages by a MidiFramer
 */
class MidiTempoParser(private val dataRepo: MidiDataRepository ) : MidiReceiver() {

    private var lastTimingClock: Long = 0L
    private var clockCount: Int = 0
    private val clockDeque = ArrayDeque<Long>(CLOCK_DEQUE_CAPACITY)
    private var songStarted: Boolean = false

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
                    if (songStarted) {
                        val delta = timestamp - lastTimingClock
                        lastTimingClock = timestamp

                        // when we have enough samples, calculate tempo
                        if (clockDeque.size == CLOCK_DEQUE_CAPACITY) {
                            calcTempo()
                            clockDeque.removeFirst() // drop oldest value
                        }
                        clockDeque.add(delta)
                    }
                }

                MidiConstants.STATUS_START -> {
                    startSong()
                }

                MidiConstants.STATUS_CONTINUE -> {
                    startSong()
                }

                MidiConstants.STATUS_STOP -> {
                    stopSong()
                }

            }

        }
    }

    private fun startSong() {
        // start with an empty set of clock deltas
        clockDeque.clear()
        lastTimingClock = System.nanoTime()
        songStarted = true
        dataRepo.pieceStarted.postValue(true)
    }

    private fun stopSong() {
        songStarted = false
        dataRepo.pieceStarted.postValue(false)
    }


    /*
     * There are 24 MIDI Clocks in every quarter note.
     * For example, for a tempo of 120 BPM, there are 120 quarter notes in every minute, so there
     * are 48 clocks per second (120*24/60).
     * The quarter note interval is 60/120 = 500ms, and the MIDI clock interval is 20.833 ms
     */
    private fun calcTempo() {
        val midiTempo = clockDeque.average() / NANOS_PER_MILLISECOND

    }

}