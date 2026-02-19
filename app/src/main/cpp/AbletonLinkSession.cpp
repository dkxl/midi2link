//
// Created by julien@macmini on 05/04/2018.
//

#include "AbletonLinkSession.h"

using Clock = ableton::link::platform::Clock;

AbletonLinkSession::AbletonLinkSession(): link(120.){
    link.enable(true);
}

void AbletonLinkSession::setEnabled(bool bEnable) {
    link.enable(bEnable);
}

double AbletonLinkSession::getTempo() {
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        return state.tempo();
    } else {
        return 0;
    }
}

double AbletonLinkSession::getPhase() {
    if (link.isEnabled()) {
        const auto time = link.clock().micros();
        return link.captureAppSessionState().phaseAtTime(time, 4);
    } else {
        return 0;
    }
}

std::size_t AbletonLinkSession::numPeers() {
    return link.numPeers();
}

bool AbletonLinkSession::isEnabled() {
    return link.isEnabled();
}

void AbletonLinkSession::setTempo(double bpm) {
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        const auto now = link.clock().micros();
        state.setTempo(bpm, now);
        link.commitAppSessionState(state);
    }
}

bool AbletonLinkSession::isPlaying() {
    return link.isEnabled() && link.captureAppSessionState().isPlaying();
}

void AbletonLinkSession::setPlaying(bool bPlay) {

    //TODO: try/catch block
    if (link.isEnabled()) {
        auto state = link.captureAppSessionState();
        const auto now = link.clock().micros();
        state.setIsPlaying(bPlay, now);
        link.commitAppSessionState(state);
    }

}
