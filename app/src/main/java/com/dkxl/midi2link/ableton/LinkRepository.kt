package com.dkxl.midi2link.ableton

import androidx.lifecycle.MutableLiveData

object LinkRepository {

    val linkIsOpen = MutableLiveData<Boolean>(false)  // Ableton Link open
    val linkBpm = MutableLiveData<Int>(0)    // tempo received from Link peers
    val linkQuantum = MutableLiveData<Int>(0) // how many beats per bar
    val linkBeatInBar = MutableLiveData<Int>(0) // current beat in this bar
    val linkIsPlaying = MutableLiveData<Boolean>(false) // track is playing
    val linkNumPeers = MutableLiveData<Int>(0) // number of Link peers
}