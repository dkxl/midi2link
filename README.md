# Midi2Link - use MIDI commands to send Ableton Link messages

Creates a virtual MIDI interface that Android applications can use to send tempo and song start/stop signals to Ableton Link applications.
The Ableton Link apps can be on the same Android host, or remote via WiFi or Ethernet.

I only had a very specific use case - I wanted to be able to use MobileSheets to tell SoundBrenner Pulse the song tempo. 
Used to work fine just with MIDI, but then SoundBrenner dropped support for MIDI...

Based on my niche use case, I only implemented tempo (BPM), song start, and song stop. 

- Midi Clock is used to calculate and update LinkBpm (int)
- Midi Start and MIDI Stop messages trigger updates to LinkIsPlaying (bool)

And the interface is currently unidirectional only - the app listens for messages from other Ableton Link nodes, 
but it does not currently do anything with them. But you should be able to add bidirectional communication if you need it for your show.





