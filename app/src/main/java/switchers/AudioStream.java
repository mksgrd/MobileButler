package switchers;

import android.media.AudioManager;

public class AudioStream {
    private int streamType = -1;
    private AudioManager audioManager = null;
    private int volume;
    private int maxVolume;
    private boolean muteState;

    public AudioStream() {
    }

    public AudioStream(int streamType) {
        setStreamType(streamType);
    }

    public AudioStream(int streamType, AudioManager audioManager) {
        this(streamType);
        initAudioManager(audioManager);
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        if (streamType == AudioManager.STREAM_ALARM || streamType == AudioManager.STREAM_DTMF ||
                streamType == AudioManager.STREAM_MUSIC || streamType == AudioManager.STREAM_NOTIFICATION ||
                streamType == AudioManager.STREAM_RING || streamType == AudioManager.STREAM_SYSTEM ||
                streamType == AudioManager.STREAM_VOICE_CALL)
            this.streamType = streamType;
        else
            throw new IllegalArgumentException("Stream type is incorrect");
    }

    public void initAudioManager(AudioManager audioManager) {
        if (streamType == -1)
            throw new RuntimeException("Stream type is not set");

        this.audioManager = audioManager;

        maxVolume = audioManager.getStreamMaxVolume(streamType);
        setVolume(audioManager.getStreamVolume(streamType));
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (audioManager == null)
            throw new RuntimeException("Audio stream is not initialized");
        if (volume < 0 || volume > maxVolume)
            throw new RuntimeException("Volume value is incorrect");
        if (volume == 0)
            setMuteState(true);
        else {
            this.volume = volume;
            audioManager.setStreamVolume(streamType, volume, 0);
        }
    }

    public int getMaxVolume() {
        return maxVolume;
    }

    public boolean isMute() {
        return muteState;
    }

    public void setMuteState(boolean muteState) {
        this.muteState = muteState;
        audioManager.setStreamVolume(streamType, muteState ? 0 : volume, 0);
    }
}
