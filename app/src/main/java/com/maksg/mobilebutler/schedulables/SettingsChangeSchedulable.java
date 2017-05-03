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
        readCalendarData(runDateTime, in);
        settings = in.createIntArray();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeCalendarData(runDateTime, dest);
        dest.writeIntArray(settings);
        dest.writeString(name);
    }

    @Override
    public void run() {
        applySettings(settings);
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
        if (context == null)
            throw new RuntimeException("Context is not set");

        long waitUntilRun = runDateTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(this, waitUntilRun);
    }

    public String getFormattedDateTimeInfo() {
        return "Запуск: " + formatDateTime(runDateTime);
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

    protected void applySettings(int[] settingsForApply) {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, settingsForApply[ALARM_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, settingsForApply[MUSIC_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, settingsForApply[NOTIFICATION_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, settingsForApply[RINGTONE_VOLUME], 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, settingsForApply[SYSTEM_VOLUME], 0);

        wifiManager.setWifiEnabled(settingsForApply[WIFI_STATE] == 1);

        if (settingsForApply[BLUETOOTH_STATE] == 1)
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    protected void writeCalendarData(Calendar calendar, Parcel parcel) {
        parcel.writeInt(calendar.get(Calendar.YEAR));
        parcel.writeInt(calendar.get(Calendar.MONTH));
        parcel.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
        parcel.writeInt(calendar.get(Calendar.HOUR_OF_DAY));
        parcel.writeInt(calendar.get(Calendar.MINUTE));
        parcel.writeInt(calendar.get(Calendar.SECOND));
        parcel.writeInt(calendar.get(Calendar.MILLISECOND));
    }

    protected void readCalendarData(Calendar calendar, Parcel parcel) {
        calendar.set(Calendar.YEAR, parcel.readInt());
        calendar.set(Calendar.MONTH, parcel.readInt());
        calendar.set(Calendar.DAY_OF_MONTH, parcel.readInt());
        calendar.set(Calendar.HOUR_OF_DAY, parcel.readInt());
        calendar.set(Calendar.MINUTE, parcel.readInt());
        calendar.set(Calendar.SECOND, parcel.readInt());
        calendar.set(Calendar.MILLISECOND, parcel.readInt());
    }

    protected String formatDateTime(Calendar calendar) {
        return DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }
}
