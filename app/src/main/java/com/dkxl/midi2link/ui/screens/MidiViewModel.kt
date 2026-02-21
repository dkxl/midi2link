package com.dkxl.midi2link.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dkxl.midi2link.midi.MidiRepository


class MidiViewModel : ViewModel() {

    // expose Read Only views to the MutableLiveData in the repository
    val midiBpm: LiveData<Int> get() = MidiRepository.midiBpm
    val midiIsPlaying: LiveData<Boolean> get() = MidiRepository.midiIsPlaying
    val midiInputIsConnected: LiveData<Boolean> get() = MidiRepository.midiInputIsConnected

}