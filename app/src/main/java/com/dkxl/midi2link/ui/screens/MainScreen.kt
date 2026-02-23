package com.dkxl.midi2link.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dkxl.midi2link.midi.MidiViewModel

@Composable
fun MainScreen(viewModel: MidiViewModel, modifier: Modifier = Modifier){

    val inputPortOpen: Boolean? by viewModel.midiInputIsConnected.observeAsState()

    val songBpm: Int? by viewModel.midiBpm.observeAsState()

    val songStarted: Boolean? by viewModel.midiIsPlaying.observeAsState()


    // tests have to allow for livedata elements to be null
    if (inputPortOpen == true) {
        Text( "MIDI Input Connected", color = Color.Green)
        Text("Beats Per Minute: $songBpm")
    } else {
        Text( "MIDI Input Disconnected", color = Color.Gray)
        Text("Beats Per Minute: $songBpm", color = Color.Gray)
    }

    // tests have to allow for livedata elements to be null
    if (inputPortOpen == true && songStarted == true) {
        Text( "Play Started", color = Color.Green)
    } else {
        Text("Play Stopped", color = Color.Gray)
    }
}
