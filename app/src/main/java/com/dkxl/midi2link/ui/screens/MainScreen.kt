package com.dkxl.midi2link.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import com.dkxl.midi2link.midi.MidiRepository

@Composable
fun MainScreen(lifecycleOwner: LifecycleOwner, modifier: Modifier = Modifier){
    // TODO: Move to a ViewModel
    // TODO: use rememberSaveable

    var inputPortOpen: Boolean by remember { mutableStateOf(false) }

    var songBpm: Int by remember { mutableIntStateOf(0) }

    var bpmValid: Boolean by remember { mutableStateOf(false) }

    var songStarted: Boolean by remember { mutableStateOf(false) }

    var tempo: Int by remember { mutableIntStateOf(0)  }
    var phase: Int by remember { mutableIntStateOf(0)  }


    MidiRepository.midiInputIsConnected.observe(lifecycleOwner) { inputPortOpen = it }

    MidiRepository.midiBpm.observe(lifecycleOwner) { songBpm = it }

    MidiRepository.midiBpmIsValid.observe(lifecycleOwner) { bpmValid = it }

    MidiRepository.midiIsPlaying.observe(lifecycleOwner) { songStarted = it }

    // TODO: is there a jetpack object to display boolean states
    Text( "Input Port Connected: " + if (inputPortOpen) "Yes" else "No")

    Text("Beats Per Minute: $songBpm")

    Text( "BPM Valid: " + if (bpmValid) "Yes" else "No")

    Text( "Song Started: " + if (songStarted) "Yes" else "No")


}
