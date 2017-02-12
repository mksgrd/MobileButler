package deviceFunctionsManager;

import android.content.Context;
import android.media.AudioManager;

public class AudioController {
    private AudioManager audioManager = null;

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private void checkedSetVolume(int streamType, int volume) {
        if (volume <= audioManager.getStreamMaxVolume(streamType) && volume >= 0)
            audioManager.setStreamVolume(streamType, volume, 0);
        else
            throw new RuntimeException("Incorrect volume");
    }

    public int getAlarmMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }

    public int getAlarmVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
    }

    public void setAlarmVolume(int alarmVolume) {
        checkedSetVolume(AudioManager.STREAM_ALARM, alarmVolume);
    }

    public int getMusicMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public int getMusicVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void setMusicVolume(int musicVolume) {
        checkedSetVolume(AudioManager.STREAM_MUSIC, musicVolume);
    }

    public int getNotificationMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
    }

    public int getNotificationVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
    }

    public void setNotificationVolume(int notificationVolume) {
        checkedSetVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume);
    }

    public int getRingtoneMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
    }

    public int getRingtoneVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_RING);
    }

    public void setRingtoneVolume(int ringtoneVolume) {
        checkedSetVolume(AudioManager.STREAM_RING, ringtoneVolume);
    }

    public int getSystemMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
    }

    public int getSystemVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    public void setSystemVolume(int systemVolume) {
        checkedSetVolume(AudioManager.STREAM_SYSTEM, systemVolume);
    }
}
