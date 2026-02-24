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

    val linkIsOpen: Boolean? by viewModel.linkIsOpen.observeAsState()
    val linkBpm: Int? by viewModel.linkBpm.observeAsState()
    val linkIsPlaying: Boolean? by viewModel.linkIsPlaying.observeAsState()
    val linkNumPeers: Int? by viewModel.linkNumPeers.observeAsState()


    // tests have to allow for livedata elements to be null
    if (inputPortOpen == true) {
        Text( "MIDI Input Connected", color = Color.Green)
        Text("MIDI Beats Per Minute: $songBpm")
    } else {
        Text( "MIDI Input Disconnected", color = Color.Gray)
        Text("MIDI Beats Per Minute: $songBpm", color = Color.Gray)
    }

    // tests have to allow for livedata elements to be null
    if (inputPortOpen == true && songStarted == true) {
        Text( "MIDI Play Started", color = Color.Green)
    } else {
        Text("MIDI Play Stopped", color = Color.Gray)
    }

    if (linkIsOpen == true) {
        Text( "Link Session Started", color = Color.Green)
        Text("Link Beats Per Minute: $linkBpm")
        Text("Link Peers: $linkNumPeers")
    } else {
        Text( "Link Session Disconnected", color = Color.Gray)
        Text("Link Beats Per Minute: $linkBpm", color = Color.Gray)
        Text("Link Peers: $linkNumPeers", color = Color.Gray)
    }

    if (linkIsOpen == true && linkIsPlaying == true) {
        Text( "Link Play Started", color = Color.Green)
    } else {
        Text("Link Play Stopped", color = Color.Gray)
    }



}
