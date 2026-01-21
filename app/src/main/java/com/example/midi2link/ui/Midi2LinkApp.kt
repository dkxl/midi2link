package com.example.midi2link.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.midi2link.ui.theme.Midi2LinkTheme

@Composable
fun Midi2LinkApp() {

    val curDevice = "None"

    HomeScreen(
        device = curDevice,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp),
    )

}

@Composable
private fun HomeScreen(device: String, modifier: Modifier = Modifier) {
    Row (
         modifier = modifier.padding(24.dp),
         verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(
                text = "Listening to Midi Device:\n$device!",
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun Midi2LinkAppPreview() {
    Midi2LinkTheme {
        Midi2LinkApp()
    }
}
