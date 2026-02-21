package com.dkxl.midi2link.midi

import androidx.lifecycle.MutableLiveData

object MidiRepository {
    val lastMidiMessageReceived = MutableLiveData<String>()   // last MIDI message received
    val midiInputIsConnected = MutableLiveData<Boolean>(false)  // MIDI input connected
    val midiBpm = MutableLiveData<Int>(0)  // tempo received from MIDI
    val midiBpmIsValid = MutableLiveData<Boolean>(false) // calculated BPM not yet stable
    val midiIsPlaying = MutableLiveData<Boolean>(false)  // track is playing
}
