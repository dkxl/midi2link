#ifndef ABLETON_LINK_SESSION_H
#define ABLETON_LINK_SESSION_H

#include <ableton/Link.hpp>

class AbletonLinkSession {

public:
    // Create a new Link instance
    explicit AbletonLinkSession(double bpm, bool bEnable);

    // Enable/disable the Link communication session
    void setEnabled(bool bEnable);
    bool isEnabled();

    // How many peers currently active?
    std::size_t numPeers();

    // Tempo of the timeline, in Beats Per Minute
    double getTempo();
    void setTempo(double bpm);

    // enable timeline play synchronisation
    void setStartStopSync(bool bEnable);
    bool isStartStopSyncEnabled();

    // Is the timeline playing
    bool isPlaying();
    void setPlaying(bool bPlay);  // only sent to peers if startStopSync is enabled

    // Get the current beat phase (e.g. 1st beat in the bar, 2nd beat, etc)
    double getPhase(double quantum);

private:
    ableton::Link link;
};

#endif // ABLETON_LINK_SESSION_H
