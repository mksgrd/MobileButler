package com.maksg.mobilebutler;

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

public class SCTask extends TimerTask implements Parcelable {
    public static final Creator<SCTask> CREATOR = new Creator<SCTask>() {
        @Override
        public SCTask createFromParcel(Parcel in) {
            return new SCTask(in);
        }

        @Override
        public SCTask[] newArray(int size) {
            return new SCTask[size];
        }
    };
    private final Timer timer = new Timer();
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private String name;
    private int alarmVolume, musicVolume, notificationVolume, ringtoneVolume, systemVolume;
    private boolean wifiEnabled, bluetoothEnabled;
    private Calendar startMoment = Calendar.getInstance();
    private AudioManager audioManager;
    private WifiManager wifiManager;

    public SCTask() {
    }

    private SCTask(Parcel in) {
        alarmVolume = in.readInt();
        musicVolume = in.readInt();
        notificationVolume = in.readInt();
        ringtoneVolume = in.readInt();
        systemVolume = in.readInt();
        wifiEnabled = in.readByte() != 0;
        bluetoothEnabled = in.readByte() != 0;
        startMoment.set(Calendar.YEAR, in.readInt());
        startMoment.set(Calendar.MONTH, in.readInt());
        startMoment.set(Calendar.DAY_OF_MONTH, in.readInt());
        startMoment.set(Calendar.HOUR_OF_DAY, in.readInt());
        startMoment.set(Calendar.MINUTE, in.readInt());
        startMoment.set(Calendar.SECOND, in.readInt());
        startMoment.set(Calendar.MILLISECOND, in.readInt());
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlarmVolume() {
        return alarmVolume;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    public int getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public int getNotificationVolume() {
        return notificationVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        this.notificationVolume = notificationVolume;
    }

    public int getRingtoneVolume() {
        return ringtoneVolume;
    }

    public void setRingtoneVolume(int ringtoneVolume) {
        this.ringtoneVolume = ringtoneVolume;
    }

    public int getSystemVolume() {
        return systemVolume;
    }

    public void setSystemVolume(int systemVolume) {
        this.systemVolume = systemVolume;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public void setBluetoothEnabled(boolean bluetoothEnabled) {
        this.bluetoothEnabled = bluetoothEnabled;
    }

    public Calendar getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Calendar startMoment) {
        startMoment.set(Calendar.SECOND, 0);
        startMoment.set(Calendar.MILLISECOND, 0);
        this.startMoment = startMoment;
    }

    public void setContext(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alarmVolume);
        dest.writeInt(musicVolume);
        dest.writeInt(notificationVolume);
        dest.writeInt(ringtoneVolume);
        dest.writeInt(systemVolume);
        dest.writeByte((byte) (wifiEnabled ? 1 : 0));
        dest.writeByte((byte) (bluetoothEnabled ? 1 : 0));
        dest.writeInt(startMoment.get(Calendar.YEAR));
        dest.writeInt(startMoment.get(Calendar.MONTH));
        dest.writeInt(startMoment.get(Calendar.DAY_OF_MONTH));
        dest.writeInt(startMoment.get(Calendar.HOUR_OF_DAY));
        dest.writeInt(startMoment.get(Calendar.MINUTE));
        dest.writeInt(startMoment.get(Calendar.SECOND));
        dest.writeInt(startMoment.get(Calendar.MILLISECOND));
        dest.writeString(name);
    }

    @Override
    public void run() {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarmVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, ringtoneVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, systemVolume, 0);

        wifiManager.setWifiEnabled(wifiEnabled);

        if (bluetoothEnabled)
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    public void schedule() {
        long waitUntilRun = startMoment.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(this, waitUntilRun);
    }

    public String getFormattedStartMoment(Context context) {
        return DateUtils.formatDateTime(context,
                startMoment.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
    }

    public String getFormattedSettings() {
        String wifiState = wifiEnabled ? "Включить" : "Выключить";
        String bluetoothState = bluetoothEnabled ? "Включить" : "Выключить";
        String text = "Громкость предупреждений: " + Integer.toString(alarmVolume) + "\n" +
                "Громкость музыки: " + Integer.toString(musicVolume) + "\n" +
                "Громкость оповещений: " + Integer.toString(notificationVolume) + "\n" +
                "Громкость мелодии звонка: " + Integer.toString(ringtoneVolume) + "\n" +
                "Громкость системных звуков: " + Integer.toString(systemVolume) + "\n" +
                "Состояние Wi-Fi: " + wifiState + "\n" +
                "Состояние Bluetooth: " + bluetoothState;

        return text;
    }
}
