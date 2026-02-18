package com.dkxl.midi2link

/**
 * Created by jbloit on 05/04/2018.
 */

object LinkWrapper {

    private var managerHandle: Long = 0

    fun create(): Boolean{
        if (managerHandle == 0L) {
            managerHandle = nativeCreateLinkManager()
        }
        return managerHandle != 0L
    }

    fun getTempo(): Double {
        if (managerHandle != 0L) {
            return nativeGetTempo(managerHandle)
        }
        return 0.0
    }
    fun getPhase(): Double {
        if (managerHandle != 0L) {
            return nativeGetPhase(managerHandle)
        }
        return 0.0
    }

    fun setEnabled(enabled: Boolean) {
        if (managerHandle != 0L) {
            nativeSetEnabled(managerHandle, enabled)
        }
    }

    external fun nativeCreateLinkManager(): Long

    // LINK API
    external fun nativeGetTempo(handle: Long): Double
    external fun nativeGetPhase(handle: Long): Double
    external fun nativeSetEnabled(handle: Long, enabled: Boolean)


}
