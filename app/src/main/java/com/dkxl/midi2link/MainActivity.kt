package com.dkxl.midi2link

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dkxl.midi2link.midi.MidiViewModel
import com.dkxl.midi2link.ui.screens.PermissionsScreen
import com.dkxl.midi2link.ui.theme.Midi2LinkTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: MidiViewModel by viewModels()

            Midi2LinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold {
                        title = "MIDI 2 Link"
                        PermissionsScreen(viewModel, Modifier.padding(it))
                        // TODO: it seems wrong that the Permissions Screen needs to know about the UI ViewModel
                    }
                }
            }
        }
    }
}

