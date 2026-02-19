//
// Based on the work of jbloit
// https://github.com/jbloit/AndroidLinkAudio
//

#ifndef LINK_MANAGER_H
#define LINK_MANAGER_H

#include <ableton/Link.hpp>

class LinkManager {

public:
    // Creates a Link instance
    LinkManager();

    // Enable/disable a Link session
    void setEnabled(bool bEnable);

    // Is Link currently enabled?
    bool isEnabled();

    // How many peers currently active?
    std::size_t numPeers();

    // Tempo of the timeline, in Beats Per Minute
    double getTempo();
    void setTempo(double bpm);

    // Get the current beat phase (e.g. 1st beat in the bar, 2nd beat, etc)
    // TODO: why is this a double instead of an int?
    double getPhase();

    // Is the timeline playing
    bool isPlaying();
    void setPlaying(bool bPlay);

private:
    ableton::Link link;
};

#endif // LINK_MANAGER_H
