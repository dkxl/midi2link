package com.dkxl.midi2link.link

import android.util.Log

const val DEFAULT_BPM: Int = 120  // beats per minute
const val DEFAULT_ENABLE_STATE: Boolean = true
const val DEFAULT_QUANTUM: Int = 4  // beats per bar

/*
 * Wrapper for accessing the native Ableton Link SDK
 */
class LinkWrapper {

    companion object {
        init {
            try {
                System.loadLibrary("midi2link")
            } catch (e: UnsatisfiedLinkError) {
                // Log the error or handle it gracefully
                Log.e("LinkWrapper", "Could not load native library", e)
            }
        }
    }

    private var handle: Long = 0L

    // TODO: allow UI to register callbacks for tempo change, playing state change, and metronome beat
    // TODO: (within c code) convert phase to beats (1st beat, 2nd beat, etc) as per Linkhut

    // Creates a Link instance, unless we already have one
    fun create(bpm: Int = DEFAULT_BPM, enable: Boolean = DEFAULT_ENABLE_STATE): Boolean {
        if (handle == 0L) {
            handle = createLinkSession(bpm.toDouble(), enable)
            LinkRepository.linkIsOpen.postValue(enable)
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
            LinkRepository.linkIsOpen.postValue(enable)
        }
    }

    // Tempo of the timeline, in Beats Per Minute
    fun setTempo(bpm: Int) {
        if (handle != 0L) {
            setTempo(handle, bpm.toDouble())
        }
    }

    fun getTempo(): Int {
        if (handle != 0L) {
            return getTempo(handle).toInt()
        }
        return 0
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
    fun getPhase(beatsPerBar: Int = DEFAULT_QUANTUM): Int {
        if (handle != 0L) {
            return getPhase(handle, beatsPerBar.toDouble()).toInt()
        }
        return 0
    }

    // Number of Link peers in the session
    fun numPeers(): Long {
        if (handle != 0L) {
            return numPeers(handle)
        }
        return 0L
    }

    // External JNI function signatures to access the Ableton Link SDK
    // See cpp/midi2link.cpp for details
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