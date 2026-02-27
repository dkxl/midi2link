package com.dkxl.midi2link.midi

import android.util.Log
import android.media.midi.MidiReceiver
import com.mobileer.miditools.MidiConstants
import kotlin.math.roundToInt

/*
 * A MidiReceiver that calculates current song tempo and song start/stop state,
 * and posts any changes to the Data Repo
 * Assumes the MIDI data has been assembled into complete messages by a MidiFramer
 */
class MidiTempoParser(private val dataRepo: MidiRepository ) : MidiReceiver() {

    private var lastBeatTimestamp: Long = 0L
    private var midiClockCount: Int = 0
    private var midiIsPlaying: Boolean = false

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
                    midiClockCount++

                    if (midiClockCount >= MIDI_CLOCKS_PER_BEAT) {
                        calculateBpm(timestamp)
                        midiClockCount = 0
                    }
                }

                MidiConstants.STATUS_START -> {
                    Log.d("TempoParser", "MIDI Start received")
                    startSong()
                }

                MidiConstants.STATUS_CONTINUE -> {
                    Log.d("TempoParser", "MIDI Continue received")
                    startSong()
                }

                MidiConstants.STATUS_STOP -> {
                    Log.d("TempoParser", "MIDI Stop received")
                    stopSong()
                }

            }

        }
    }

    private fun startSong() {
        midiIsPlaying = true
        dataRepo.midiIsPlaying.postValue(true)
    }

    private fun stopSong() {
        midiIsPlaying = false
        dataRepo.midiIsPlaying.postValue(false)
    }


    /*
     * There are 24 MIDI Clocks in every quarter note.
     * For example, for a tempo of 120 BPM, there are 120 quarter notes in every minute, so there
     * are 48 clocks per second (120*24/60).
     * The quarter note interval is 60/120 = 500ms, and the MIDI clock interval is 20.833 ms
     */
    private fun calculateBpm(midiClockTimestamp: Long)  {

        val nanosPerMinuteAsDouble = NANOS_PER_MINUTE.toDouble()

        val beatIntervalNanoSeconds = midiClockTimestamp - lastBeatTimestamp
        lastBeatTimestamp = midiClockTimestamp

        if (beatIntervalNanoSeconds >= MAX_BEAT_INTERVAL) {
            dataRepo.midiBpmIsValid.postValue(false)
            dataRepo.midiBpm.postValue(0)
            return
        }

        // Need the division to include a Double, so the result is a Double that can be rounded to
        // nearest Int; Long divided by Long truncates towards zero so beats per minute could be
        // unstable if the arrival rate of MIDI clock messages drifts
        val beatsPerMinute = (nanosPerMinuteAsDouble/beatIntervalNanoSeconds).roundToInt()
        dataRepo.midiBpm.postValue(beatsPerMinute)
    }

}