package com.dkxl.midi2link.midi

import androidx.lifecycle.MutableLiveData

// TODO review using Flow States instead of Live Data
object MidiDataRepository {
    val lastMessage = MutableLiveData<String>()   // last MIDI message received
    val inputPortOpen = MutableLiveData<Boolean>(false)  // MIDI input connected

    val pieceTempo = MutableLiveData<Int>(0)  // tempo of the current piece of music

    val pieceStarted = MutableLiveData<Boolean>(false)

    val linkOpen = MutableLiveData<Boolean>(false)  // Ableton Link open

}
