package switchers;

import android.content.Context;
import android.media.AudioManager;

public class SoundSwitcher {
    private AudioManager audioManager;

    private int notificationVolume;
    private int alarmVolume;
    private int musicVolume;
    private int ringVolume;
    private int systemVolume;

    public SoundSwitcher() {
    }

    public SoundSwitcher(Context context) {
        setContext(context);
    }

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        notificationVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        alarmVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        ringVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    private void setStreamVolume(int streamType, int volume) {
        if (volume >= 0 && volume <= audioManager.getStreamMaxVolume(streamType))
            audioManager.setStreamVolume(streamType, volume, AudioManager.FLAG_SHOW_UI);
        else
            throw new IllegalArgumentException("Volume value is incorrect");
    }

    public int getNotificationVolume() {
        return notificationVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume);
        this.notificationVolume = notificationVolume;
    }

    public int getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(int alarmVolume) {
        setStreamVolume(AudioManager.STREAM_ALARM, alarmVolume);
        this.alarmVolume = alarmVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume);
        this.musicVolume = musicVolume;
    }

    public int getRingVolume() {
        return ringVolume;
    }

    public void setRingVolume(int ringVolume) {
        setStreamVolume(AudioManager.STREAM_RING, ringVolume);
        this.ringVolume = ringVolume;
    }

    public int getSystemVolume() {
        return systemVolume;
    }

    public void setSystemVolume(int systemVolume) {
        setStreamVolume(AudioManager.STREAM_SYSTEM, systemVolume);
        this.systemVolume = systemVolume;
    }
}
