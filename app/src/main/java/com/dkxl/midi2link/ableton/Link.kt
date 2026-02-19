package com.dkxl.midi2link.ableton

object Link {

    //TODO: keep the managerHandle within the native code, don't keep passing it

    private var managerHandle: Long = 0

    fun create(): Boolean{
        if (managerHandle == 0L) {
            managerHandle = createLinkManager()
        }
        return managerHandle != 0L
    }

    fun getTempo(): Double {
        if (managerHandle != 0L) {
            return getTempo(managerHandle)
        }
        return 0.0
    }
    fun getPhase(): Double {
        if (managerHandle != 0L) {
            return getPhase(managerHandle)
        }
        return 0.0
    }

    fun setEnabled(enabled: Boolean) {
        if (managerHandle != 0L) {
            setEnabled(managerHandle, enabled)
        }
    }

    private external fun createLinkManager(): Long
    private external fun getTempo(handle: Long): Double
    private external fun getPhase(handle: Long): Double
    private external fun setEnabled(handle: Long, enabled: Boolean)

}