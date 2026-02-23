package com.dkxl.midi2link.midi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MidiViewModel : ViewModel() {

    // expose Read Only views to the MutableLiveData in the repository
    val midiBpm: LiveData<Int> = MidiRepository.midiBpm
    val midiIsPlaying: LiveData<Boolean> = MidiRepository.midiIsPlaying
    val midiInputIsConnected: LiveData<Boolean> = MidiRepository.midiInputIsConnected

}