//
// Created by julien@macmini on 05/04/2018.
//

#include "LinkManager.h"

using Clock = ableton::link::platform::Clock;

LinkManager::LinkManager(): link(120.){
    link.enable(true);
}

void LinkManager::setEnabled(bool bEnable) {
    link.enable(bEnable);
}

double LinkManager::getTempo() {
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        return state.tempo();
    } else {
        return 0;
    }
}

double LinkManager::getPhase() {
    if (link.isEnabled()) {
        const auto time = link.clock().micros();
        return link.captureAppSessionState().phaseAtTime(time, 4);
    } else {
        return 0;
    }
}

std::size_t LinkManager::numPeers() {
    return link.numPeers();
}

bool LinkManager::isEnabled() {
    return link.isEnabled();
}

void LinkManager::setTempo(double bpm) {
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        const auto now = link.clock().micros();
        state.setTempo(bpm, now);
        link.commitAppSessionState(state);
    }
}

bool LinkManager::isPlaying() {
    return link.isEnabled() && link.captureAppSessionState().isPlaying();
}

void LinkManager::setPlaying(bool bPlay) {

    //TODO: try/catch block
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        const auto now = link.clock().micros();
        state.setIsPlaying(bPlay, now);
        link.commitAppSessionState(state);
    }

}
