package com.dkxl.midi2link.midi

import android.media.midi.MidiReceiver
import androidx.lifecycle.MutableLiveData
import com.mobileer.miditools.MidiPrinter
import java.util.Locale

private const val NANOS_PER_MILLISECOND = 1000000L
private const val NANOS_PER_SECOND = 1000L

/*
 * A MidiReceiver that parses data into a string and posts to a LiveData instance
 * Assumes the data has been assembled into complete messages by a MidiFramer
 */
class MidiDataParser(private val liveData: MutableLiveData<String>) : MidiReceiver() {

    private val startTime: Long = System.nanoTime()
    private var lastTimeStamp: Long = 0

    override fun onSend(
        msg: ByteArray?,
        offset: Int,
        count: Int,
        timestamp: Long
    ) {
        if (msg != null) {
            val sb = StringBuilder()
            if (timestamp == 0L) {
                sb.append(String.format("-----0----: "))
            } else {
                val monoTime = timestamp - startTime
                val delayTimeNanos = timestamp - System.nanoTime()
                val delayTimeMillis = (delayTimeNanos / NANOS_PER_MILLISECOND).toInt()
                val seconds = monoTime.toDouble() / NANOS_PER_SECOND
                // Mark timestamps that are out of order.
                sb.append(if (timestamp < lastTimeStamp) "*" else " ")
                lastTimeStamp = timestamp
                sb.append(String.Companion.format(Locale.UK, "%10.3f (%2d): ", seconds, delayTimeMillis))
            }
            sb.append(MidiPrinter.formatBytes(msg, offset, count))
            sb.append(": ")
            sb.append(MidiPrinter.formatMessage(msg, offset, count))
            liveData.postValue(sb.toString())
        }
    }

}