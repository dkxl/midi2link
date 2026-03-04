#include "AbletonLinkSession.h"

//using Clock = ableton::link::platform::Clock;

AbletonLinkSession::AbletonLinkSession(double bpm, bool bEnable): link(bpm) {
    link.enable(bEnable);
}

void AbletonLinkSession::setEnabled(bool bEnable) {
    link.enable(bEnable);
}
bool AbletonLinkSession::isEnabled() {
    return link.isEnabled();
}

std::size_t AbletonLinkSession::numPeers() {
    return link.numPeers();
}

double AbletonLinkSession::getTempo() {
    auto state = link.captureAppSessionState();
    return state.tempo();
}

void AbletonLinkSession::setTempo(double bpm) {
    auto state = link.captureAppSessionState();
    const auto now = link.clock().micros();
    state.setTempo(bpm, now);
    link.commitAppSessionState(state);
}

bool AbletonLinkSession::isPlaying() {
    return link.isEnabled() && link.captureAppSessionState().isPlaying();
}

void AbletonLinkSession::setStartStopSync(bool bEnable) {
    link.enableStartStopSync(bEnable);
}

bool AbletonLinkSession::isStartStopSyncEnabled() {
    return link.isStartStopSyncEnabled();
}

void AbletonLinkSession::setPlaying(bool bPlay) {
    auto state = link.captureAppSessionState();
    const auto now = link.clock().micros();
    state.setIsPlaying(bPlay, now);
    link.commitAppSessionState(state);
}

double AbletonLinkSession::getPhase(double quantum) {
    const auto time = link.clock().micros();
    return link.captureAppSessionState().phaseAtTime(time, quantum);
}
