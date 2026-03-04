package com.dkxl.midi2link.link

import java.util.TimerTask

class LinkPoller(
    private val abletonLink: LinkWrapper
) : TimerTask() {
    override fun run() {
        LinkRepository.linkNumPeers.postValue(abletonLink.numPeers())
        LinkRepository.linkIsPlaying.postValue(abletonLink.isPlaying())
        LinkRepository.linkBpm.postValue(abletonLink.getTempo())
    }
}