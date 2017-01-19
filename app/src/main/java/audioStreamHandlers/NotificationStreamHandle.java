package audioStreamHandlers;

import android.media.AudioManager;

public class NotificationStreamHandle {
    private AudioManager audioManager = null;
    private int volume;
    private int maxVolume;
    private boolean muteState;

    public NotificationStreamHandle() {
    }

    public NotificationStreamHandle(AudioManager audioManager) {
        setAudioManager(audioManager);
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        setVolume(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (audioManager == null)
            throw new RuntimeException("Audio manager is not set");
        if (volume < 0 || volume > maxVolume)
            throw new RuntimeException("Volume value is incorrect");
        if (volume == 0)
            setMuteState(true);
        else {
            this.volume = volume;
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, 0);
        }
    }

    public boolean isMuted() {
        return muteState;
    }

    public void setMuteState(boolean muteState) {
        this.muteState = muteState;
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, muteState ? 0 : volume, 0);
    }

    public int getMaxVolume() {
        return maxVolume;
    }
}
