package switchers;

import android.content.Context;
import android.media.AudioManager;

public class SoundSwitcher {
    private AudioManager audioManager = null;
    private boolean notificationMuteState = true;
    private boolean alarmMuteState = true;
    private boolean musicMuteState = true;
    private boolean ringMuteState = true;
    private boolean systemMuteState = true;

    public SoundSwitcher() {
    }

    public SoundSwitcher(Context context) {
        setContext(context);
    }

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean isNotificationMute() {
        return notificationMuteState;
    }

    public void setNotificationMuteState(boolean notificationMuteState) {
        this.notificationMuteState = notificationMuteState;
        audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, notificationMuteState);
    }

    public boolean isAlarmMute() {
        return alarmMuteState;
    }

    public void setAlarmMuteState(boolean alarmMuteState) {
        this.alarmMuteState = alarmMuteState;
        audioManager.setStreamMute(AudioManager.STREAM_ALARM, alarmMuteState);
    }

    public boolean isMusicMute() {
        return musicMuteState;
    }

    public void setMusicMuteState(boolean musicMuteState) {
        this.musicMuteState = musicMuteState;
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, musicMuteState);
    }

    public boolean isRingMute() {
        return ringMuteState;
    }

    public void setRingMuteState(boolean ringMuteState) {
        this.ringMuteState = ringMuteState;
        audioManager.setStreamMute(AudioManager.STREAM_RING, ringMuteState);
    }

    public boolean isSystemMute() {
        return systemMuteState;
    }

    public void setSystemMuteState(boolean systemMuteState) {
        this.systemMuteState = systemMuteState;
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, systemMuteState);
    }

    public void setMuteAllState(boolean muteAllState) {
        setNotificationMuteState(muteAllState);
        setAlarmMuteState(muteAllState);
        setMusicMuteState(muteAllState);
        setRingMuteState(muteAllState);
        setSystemMuteState(muteAllState);
    }

    public boolean isMuteAll() {
        return notificationMuteState && alarmMuteState && musicMuteState && ringMuteState && systemMuteState;
    }
}
