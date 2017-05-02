package com.maksg.mobilebutler.schedulables;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.SparseIntArray;

import java.util.Calendar;
import java.util.TimerTask;

public abstract class SettingsSchedulable extends TimerTask implements Parcelable {
    public static final int ALARM_VOLUME = 0, MUSIC_VOLUME = 1, NOTIFICATION_VOLUME = 2, RINGTONE_VOLUME = 3,
            SYSTEM_VOLUME = 4, WIFI_STATE = 5, BLUETOOTH_STATE = 6;

    private SparseIntArray settings = new SparseIntArray();
    private String name;
    private Calendar scheduleDateTime = Calendar.getInstance();
    private AudioManager audioManager;
    private WifiManager wifiManager;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public abstract void schedule();

    public abstract String getFormattedScheduleInfo();

    public abstract String getFormattedSettings();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getScheduleDateTime() {
        return scheduleDateTime;
    }

    public void setScheduleDateTime(Calendar scheduleDateTime) {
        this.scheduleDateTime = scheduleDateTime;
    }

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void putSettings(int key, int value) {
        settings.put(key, value);
    }
}
