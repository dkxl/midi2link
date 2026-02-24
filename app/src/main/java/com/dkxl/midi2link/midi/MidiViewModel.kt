package com.dkxl.midi2link.midi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dkxl.midi2link.link.LinkRepository

class MidiViewModel : ViewModel() {

    // expose Read Only views to the MutableLiveData in the MIDI repository
    val midiBpm: LiveData<Int> = MidiRepository.midiBpm
    val midiIsPlaying: LiveData<Boolean> = MidiRepository.midiIsPlaying
    val midiInputIsConnected: LiveData<Boolean> = MidiRepository.midiInputIsConnected

    // expose Read Only views of the MutableLiveData in the Link repository
    val linkIsOpen: LiveData<Boolean> = LinkRepository.linkIsOpen
    val linkBpm: LiveData<Int>  = LinkRepository.linkBpm
    val linkIsPlaying: LiveData<Boolean>  = LinkRepository.linkIsPlaying
    val linkQuantum: LiveData<Int> = LinkRepository.linkQuantum
    val linkBeatInBar: LiveData<Int> = LinkRepository.linkBeatInBar
    val linkNumPeers: LiveData<Int> = LinkRepository.linkNumPeers


}