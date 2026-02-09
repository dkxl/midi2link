/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Original Code: https://github.com/philburk/android-midisuite.git
 * This version ported from Java to Kotlin 09-02-2026
 *
 */
package com.mobileer.miditools

import android.media.midi.MidiDeviceInfo
import com.mobileer.miditools.MidiConstants.getBytesPerMessage

/**
 * Format a MIDI message for printing.
 */
object MidiPrinter {
    val CHANNEL_COMMAND_NAMES: Array<String?> = arrayOf<String?>(
        "NoteOff", "NoteOn",
        "PolyTouch", "Control", "Program", "Pressure", "Bend"
    )
    val SYSTEM_COMMAND_NAMES: Array<String?> = arrayOf<String?>(
        "SysEx",  // F0
        "TimeCode",  // F1
        "SongPos",  // F2
        "SongSel",  // F3
        "F4",  // F4
        "F5",  // F5
        "TuneReq",  // F6
        "EndSysex",  // F7
        "TimingClock",  // F8
        "F9",  // F9
        "Start",  // FA
        "Continue",  // FB
        "Stop",  // FC
        "FD",  // FD
        "ActiveSensing",  // FE
        "Reset" // FF
    )

    fun getName(status: Int): String? {
        if (status >= 0xF0) {
            val index = status and 0x0F
            return SYSTEM_COMMAND_NAMES[index]
        } else if (status >= 0x80) {
            val index = (status shr 4) and 0x07
            return CHANNEL_COMMAND_NAMES[index]
        } else {
            return "data"
        }
    }

    fun formatBytes(data: ByteArray, offset: Int, count: Int): String {
        val sb = StringBuilder()
        for (i in 0..<count) {
            sb.append(String.format(" %02X", data[offset + i]))
        }
        return sb.toString()
    }

    // This assumes the message has been aligned using a MidiFramer
    // so that the first byte is a status byte.
    fun formatMessage(data: ByteArray, offset: Int, count: Int): String {
        var offset = offset
        val sb = StringBuilder()
        val statusByte = data[offset++]
        val status = statusByte.toInt() and 0xFF
        sb.append(getName(status)).append("(")
        val numData = getBytesPerMessage(statusByte) - 1
        if ((status >= 0x80) && (status < 0xF0)) { // channel message
            val channel = status and 0x0F
            // Add 1 for humans who think channels are numbered 1-16.
            sb.append((channel + 1)).append(", ")
        }
        for (i in 0..<numData) {
            if (i > 0) {
                sb.append(", ")
            }
            sb.append(data[offset++].toInt())
        }
        sb.append(")")
        return sb.toString()
    }

    fun formatDeviceInfo(info: MidiDeviceInfo?): String {
        val sb = StringBuilder()
        if (info != null) {
            val properties = info.getProperties()
            for (key in properties.keySet()) {
                val value = properties.get(key)
                sb.append(key).append(" = ").append(value).append('\n')
            }
            for (port in info.getPorts()) {
                sb.append(
                    if (port.getType() == MidiDeviceInfo.PortInfo.TYPE_INPUT)
                        "input"
                    else
                        "output"
                )
                sb.append("[").append(port.getPortNumber()).append("] = \"").append(
                    port.getName()
                            + "\"\n"
                )
            }
        }
        return sb.toString()
    }
}