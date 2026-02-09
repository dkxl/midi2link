package com.dkxl.midi2link

import androidx.lifecycle.MutableLiveData

object MidiDataRepository {
    val lastMessage = MutableLiveData<String>()
    val inputPortOpen = MutableLiveData<Boolean>()
}