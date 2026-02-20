package com.dkxl.midi2link.ableton

object Link {

    private const val DEFAULT_BPM = 120.0
    private const val DEFAULT_ENABLE_STATE = true
    private const val DEFAULT_BEATS_PER_BAR = 4.0

    private var handle: Long = 0L

    // Creates a Link instance, unless we already have one
    fun create(bpm: Double = DEFAULT_BPM, enable: Boolean = DEFAULT_ENABLE_STATE): Boolean {
        if (handle == 0L) {
            handle = createLinkSession(bpm, enable)
        }
        return handle != 0L
    }

    // Is Link currently enabled?
    fun isEnabled() : Boolean {
        return handle != 0L && isEnabled(handle)
    }

    // Enable/disable a Link session
    fun setEnabled(enable: Boolean) {
        if (handle != 0L) {
            setEnabled(handle, enable)
        }
    }

    // Tempo of the timeline, in Beats Per Minute
    fun setTempo(bpm: Double) {
        if (handle != 0L) {
            setTempo(handle, bpm)
        }
    }

    fun getTempo(): Double {
        if (handle != 0L) {
            return getTempo(handle)
        }
        return 0.0
    }

    // Is the timeline playing
    fun isPlaying() : Boolean {
        return handle != 0L && isPlaying(handle)
    }

    fun setPlaying(play: Boolean) {
        if (handle != 0L) {
            setPlaying(handle, play)
        }
    }

    // Get the current beat phase (e.g. 1st beat in the bar, 2nd beat, etc)
    fun getPhase(beatsPerBar: Double = DEFAULT_BEATS_PER_BAR): Double {
        if (handle != 0L) {
            return getPhase(handle, beatsPerBar)
        }
        return 0.0
    }

    // Number of Link peers in the session
    fun numPeers(): Long {
        if (handle != 0L) {
            return numPeers(handle)
        }
        return 0L
    }


    private external fun createLinkSession(bpm: Double, enable: Boolean): Long
    private external fun setEnabled(handle: Long, enable: Boolean)
    private external fun isEnabled(handle: Long) : Boolean

    private external fun setTempo(handle: Long, bpm: Double)
    private external fun getTempo(handle: Long): Double

    private external fun setPlaying(handle: Long, play: Boolean)
    private external fun isPlaying(handle: Long) : Boolean

    private external fun getPhase(handle: Long, quantum: Double): Double

    private external fun numPeers(handle: Long): Long

}