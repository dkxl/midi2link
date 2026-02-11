package com.dkxl.midi2link.midi

const val NANOS_PER_MILLISECOND = 1000000L
const val NANOS_PER_SECOND = NANOS_PER_MILLISECOND * 1000L

// how many MIDI clock timestamps to store in the deque
const val CLOCK_DEQUE_CAPACITY = 10

// How many MIDI clock pulses per quarter note
const val CLOCKS_PER_QUARTER_NOTE = 24
