package com.dkxl.midi2link

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dkxl.midi2link.ui.screens.Midi2LinkMain
import com.dkxl.midi2link.ui.theme.Midi2LinkTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lifecycleOwner = this

        enableEdgeToEdge()
        setContent {
            Midi2LinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold {
                        title = "MIDI 2 Link"
                        Midi2LinkMain(lifecycleOwner, Modifier.padding(it))
                    }
                }
            }
        }
    }
}

