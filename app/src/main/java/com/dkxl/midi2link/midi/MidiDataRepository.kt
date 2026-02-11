package com.dkxl.midi2link.midi

import androidx.lifecycle.MutableLiveData

object MidiDataRepository {
    val lastMessage = MutableLiveData<String>()
    val inputPortOpen = MutableLiveData<Boolean>()

}