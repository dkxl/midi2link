#ifndef ABLETON_LINK_SESSION_H
#define ABLETON_LINK_SESSION_H

#include <ableton/Link.hpp>

class AbletonLinkSession {

public:
    // Creates a new Link instance
    AbletonLinkSession();

    // Enable/disable the session
    void setEnabled(bool bEnable);
    bool isEnabled();

    // How many peers currently active?
    std::size_t numPeers();

    // Tempo of the timeline, in Beats Per Minute
    double getTempo();
    void setTempo(double bpm);

    // Is the timeline playing
    bool isPlaying();
    void setPlaying(bool bPlay);

    // Get the current beat phase (e.g. 1st beat in the bar, 2nd beat, etc)
    // TODO: why is this a double instead of an int?
    double getPhase();

private:
    ableton::Link link;
};

#endif // ABLETON_LINK_SESSION_H
