package com.maksg.mobilebutler.schedulables;

import android.media.AudioManager;
import android.os.Parcel;

import java.util.Calendar;
import java.util.TimerTask;

public class SettingsChangeEvent extends SettingsChangeTask {
    public static final Creator<SettingsChangeEvent> CREATOR = new Creator<SettingsChangeEvent>() {
        @Override
        public SettingsChangeEvent createFromParcel(Parcel in) {
            return new SettingsChangeEvent(in);
        }

        @Override
        public SettingsChangeEvent[] newArray(int size) {
            return new SettingsChangeEvent[size];
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

    public SettingsChangeEvent(SettingsChangeTask settingsChangeTask) {
        runDateTime = settingsChangeTask.runDateTime;
        settings = settingsChangeTask.settings;
        timer = settingsChangeTask.timer;
        audioManager = settingsChangeTask.audioManager;
        wifiManager = settingsChangeTask.wifiManager;
        bluetoothAdapter = settingsChangeTask.bluetoothAdapter;
        name = settingsChangeTask.name;
        context = settingsChangeTask.context;
    }

    protected SettingsChangeEvent(Parcel in) {
        super(in);
        readCalendarData(restoreDateTime, in);
        restoreSettings = in.createIntArray();
    }

    @Override
    public Calendar getStartActionDateTime() {
        return restoreDateTime;
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
    public boolean cancel() {
        return super.cancel() && restoreSettingsTask.cancel();
    }

    @Override
    public String getFormattedDateTimeInfo() {
        return "Начало: " + formatDateTime(runDateTime) + "\n" +
                "Окончание: " + formatDateTime(restoreDateTime);
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
