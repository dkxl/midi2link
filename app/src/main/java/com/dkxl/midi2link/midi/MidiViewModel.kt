package com.dkxl.midi2link.midi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MidiViewModel : ViewModel() {

    // expose Read Only views to the MutableLiveData in the repository
    val midiBpm: LiveData<Int> get() = MidiRepository.midiBpm
    val midiIsPlaying: LiveData<Boolean> get() = MidiRepository.midiIsPlaying
    val midiInputIsConnected: LiveData<Boolean> get() = MidiRepository.midiInputIsConnected

}