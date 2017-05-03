package com.maksg.mobilebutler.schedulables;

import android.media.AudioManager;
import android.os.Parcel;

import java.util.Calendar;
import java.util.TimerTask;

public class SettingsEvent extends SettingsChangeSchedulable {
    public static final Creator<SettingsEvent> CREATOR = new Creator<SettingsEvent>() {
        @Override
        public SettingsEvent createFromParcel(Parcel in) {
            return new SettingsEvent(in);
        }

        @Override
        public SettingsEvent[] newArray(int size) {
            return new SettingsEvent[size];
        }
    };
    private Calendar restoreDateTime = Calendar.getInstance();
    private int[] restoreSettings = new int[SETTINGS_COUNT];
    private TimerTask restoreSettingsTask = new TimerTask() {
        @Override
        public void run() {
            applySettings(restoreSettings);
        }
    };

    public SettingsEvent() {
    }

    protected SettingsEvent(Parcel in) {
        super(in);
        readCalendarData(restoreDateTime, in);
        restoreSettings = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        writeCalendarData(restoreDateTime, dest);
        dest.writeIntArray(restoreSettings);
    }

    @Override
    public void schedule() {
        super.schedule();
        long waitUntilRun = restoreDateTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(restoreSettingsTask, waitUntilRun);
    }

    @Override
    public String getFormattedDateTimeInfo() {
        return "Начало: " + formatDateTime(runDateTime) + "\n" +
                "Окончание: " + formatDateTime(restoreDateTime);
    }

    public Calendar getRestoreDateTime() {
        return restoreDateTime;
    }

    public void setRestoreDateTime(Calendar restoreDateTime) {
        this.restoreDateTime = restoreDateTime;
    }

    public void saveCurrentSettings() {
        restoreSettings[ALARM_VOLUME] = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        restoreSettings[MUSIC_VOLUME] = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        restoreSettings[NOTIFICATION_VOLUME] = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        restoreSettings[RINGTONE_VOLUME] = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        restoreSettings[SYSTEM_VOLUME] = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        restoreSettings[WIFI_STATE] = wifiManager.isWifiEnabled() ? 1 : 0;
        restoreSettings[BLUETOOTH_STATE] = bluetoothAdapter.isEnabled() ? 1 : 0;
    }
}
