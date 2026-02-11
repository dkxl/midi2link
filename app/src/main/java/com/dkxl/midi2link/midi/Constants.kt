package com.dkxl.midi2link.midi

const val NANOS_PER_MILLISECOND = 1000000L
const val NANOS_PER_SECOND = NANOS_PER_MILLISECOND * 1000L
const val NANOS_PER_MINUTE = NANOS_PER_SECOND * 60L

// How many MIDI clock pulses per quarter note
const val MIDI_CLOCKS_PER_BEAT = 24

const val SLOWEST_BPM = 10

const val FASTEST_BPM = 240

// How long before we consider the last clock pulse to be too old
const val MAX_BEAT_INTERVAL = SLOWEST_BPM * MIDI_CLOCKS_PER_BEAT * NANOS_PER_MINUTE
