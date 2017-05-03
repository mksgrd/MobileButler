package com.maksg.mobilebutler.schedulables;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsChangeSchedulable extends TimerTask implements Parcelable {
    public static final int ALARM_VOLUME = 0, MUSIC_VOLUME = 1, NOTIFICATION_VOLUME = 2, RINGTONE_VOLUME = 3,
            SYSTEM_VOLUME = 4, WIFI_STATE = 5, BLUETOOTH_STATE = 6;
    public static final int SETTINGS_COUNT = 6;
    public static final Creator<SettingsChangeSchedulable> CREATOR = new Creator<SettingsChangeSchedulable>() {
        @Override
        public SettingsChangeSchedulable createFromParcel(Parcel in) {
            return new SettingsChangeSchedulable(in);
        }

        @Override
        public SettingsChangeSchedulable[] newArray(int size) {
            return new SettingsChangeSchedulable[size];
        }
    };
    protected Calendar runDateTime = Calendar.getInstance();
    protected int[] settings = new int[SETTINGS_COUNT];
    protected Timer timer = new Timer();
    protected AudioManager audioManager;
    protected WifiManager wifiManager;
    protected BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    protected String name;
    protected Context context;

    public SettingsChangeSchedulable() {
    }

    protected SettingsChangeSchedulable(Parcel in) {
        runDateTime.set(Calendar.YEAR, in.readInt());
        runDateTime.set(Calendar.MONTH, in.readInt());
        runDateTime.set(Calendar.DAY_OF_MONTH, in.readInt());
        runDateTime.set(Calendar.HOUR_OF_DAY, in.readInt());
        runDateTime.set(Calendar.MINUTE, in.readInt());
        runDateTime.set(Calendar.SECOND, in.readInt());
        runDateTime.set(Calendar.MILLISECOND, in.readInt());
        settings = in.createIntArray();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(runDateTime.get(Calendar.YEAR));
        dest.writeInt(runDateTime.get(Calendar.MONTH));
        dest.writeInt(runDateTime.get(Calendar.DAY_OF_MONTH));
        dest.writeInt(runDateTime.get(Calendar.HOUR_OF_DAY));
        dest.writeInt(runDateTime.get(Calendar.MINUTE));
        dest.writeInt(runDateTime.get(Calendar.SECOND));
        dest.writeInt(runDateTime.get(Calendar.MILLISECOND));
        dest.writeIntArray(settings);
        dest.writeString(name);
    }

    @Override
    public void run() {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, settings[ALARM_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, settings[MUSIC_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, settings[NOTIFICATION_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, settings[RINGTONE_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, settings[SYSTEM_VOLUME], 0);

        wifiManager.setWifiEnabled(settings[WIFI_STATE] == 1);

        if (settings[BLUETOOTH_STATE] == 1)
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    public Calendar getRunDateTime() {
        return runDateTime;
    }

    public void setRunDateTime(Calendar runDateTime) {
        runDateTime.set(Calendar.MILLISECOND, 0);
        runDateTime.set(Calendar.SECOND, 0);
        this.runDateTime = runDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSettings(int key, int value) {
        if (key >= 0 && key <= SETTINGS_COUNT)
            settings[key] = value;
        else
            throw new IndexOutOfBoundsException("Settings key is incorrect");
    }

    public void setContext(Context context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void schedule() {
        long waitUntilRun = runDateTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(this, waitUntilRun);
    }

    public String getFormattedRunDateTime() {
        return DateUtils.formatDateTime(context,
                runDateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public String getFormattedSettingsInfo() {
        String wifiState = settings[WIFI_STATE] == 1 ? "Включить" : "Выключить";
        String bluetoothState = settings[BLUETOOTH_STATE] == 1 ? "Включить" : "Выключить";

        return "Громкость предупреждений: " + Integer.toString(settings[ALARM_VOLUME]) + "\n" +
                "Громкость музыки: " + Integer.toString(settings[MUSIC_VOLUME]) + "\n" +
                "Громкость оповещений: " + Integer.toString(settings[NOTIFICATION_VOLUME]) + "\n" +
                "Громкость мелодии звонка: " + Integer.toString(settings[RINGTONE_VOLUME]) + "\n" +
                "Громкость системных звуков: " + Integer.toString(settings[SYSTEM_VOLUME]) + "\n" +
                "Состояние Wi-Fi: " + wifiState + "\n" +
                "Состояние Bluetooth: " + bluetoothState;
    }
}
