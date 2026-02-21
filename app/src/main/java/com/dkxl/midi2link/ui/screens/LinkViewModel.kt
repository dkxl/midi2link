package com.dkxl.midi2link.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dkxl.midi2link.ableton.LinkRepository


class LinkViewModel : ViewModel() {

    // expose Read Only views of the MutableLiveData in the repository
    val linkIsOpen: LiveData<Boolean> get() = LinkRepository.linkIsOpen
    val linkBpm: LiveData<Int> get() = LinkRepository.linkBpm
    val linkIsPlaying: LiveData<Boolean> get() = LinkRepository.linkIsPlaying
    val linkQuantum: LiveData<Int> get() = LinkRepository.linkQuantum
    val linkBeatInBar: LiveData<Int> get() = LinkRepository.linkBeatInBar
    val linkNumPeers: LiveData<Int> get() = LinkRepository.linkNumPeers

}