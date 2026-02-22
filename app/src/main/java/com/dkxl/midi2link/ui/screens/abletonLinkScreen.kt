package com.dkxl.midi2link.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
private fun AbletonLinkScreen(
    isRunning: Boolean,
    curTempo: Int,
    curPhase: Int,
    onTempoRefresh: (Int) -> Unit,
    onPhaseRefresh: (Int) -> Unit,
    onRunStateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp )
    )
    {
        Text("Ableton Link", fontWeight = FontWeight.Bold)

//        Button(
//            onClick= {
//                val tempo = AbletonLinkWrapper.getTempo()
//                onTempoRefresh(tempo) }
//        ) {
//            Text("Refresh Song Tempo")
//        }
//        Text("Current Tempo: $curTempo")
//
//        Button(
//            onClick= {
//                val phase = AbletonLinkWrapper.getPhase()
//                onPhaseRefresh(phase)
//            }
//        ) {
//            Text("Refresh Song Phase")
//        }
//        Text("Current Phase: $curPhase")
//
//
//        Button(
//            onClick = {
//                onRunStateChange(!isRunning)
//                AbletonLinkWrapper.setEnabled(true)
//            }
//        ) {
//            Text(if (isRunning) "Stop Link" else "Start Link")
//        }

    }

}
