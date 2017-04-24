package scheduler;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsChangeTask extends TimerTask implements Parcelable {
    public static final Creator<SettingsChangeTask> CREATOR = new Creator<SettingsChangeTask>() {
        @Override
        public SettingsChangeTask createFromParcel(Parcel in) {
            return new SettingsChangeTask(in);
        }

        @Override
        public SettingsChangeTask[] newArray(int size) {
            return new SettingsChangeTask[size];
        }
    };
    private int alarmVolume, musicVolume, notificationVolume, ringtoneVolume, systemVolume;
    private boolean wifiState, bluetoothState;
    private Calendar startMoment = Calendar.getInstance();
    private Timer timer = new Timer();
    private AudioManager audioManager;
    private WifiManager wifiManager;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public SettingsChangeTask() {
    }

    private SettingsChangeTask(Parcel in) {
        alarmVolume = in.readInt();
        musicVolume = in.readInt();
        notificationVolume = in.readInt();
        ringtoneVolume = in.readInt();
        systemVolume = in.readInt();
        wifiState = in.readByte() != 0;
        bluetoothState = in.readByte() != 0;
        startMoment.set(Calendar.YEAR, in.readInt());
        startMoment.set(Calendar.MONTH, in.readInt());
        startMoment.set(Calendar.DAY_OF_MONTH, in.readInt());
        startMoment.set(Calendar.HOUR_OF_DAY, in.readInt());
        startMoment.set(Calendar.MINUTE, in.readInt());
        startMoment.set(Calendar.SECOND, in.readInt());
        startMoment.set(Calendar.MILLISECOND, in.readInt());
    }

    public Calendar getStartMoment() {
        return startMoment;
    }

    public void setStartMoment(Calendar startMoment) {
        this.startMoment = startMoment;
    }

    public void setAlarmVolume(int alarmVolume) {
        this.alarmVolume = alarmVolume;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        this.notificationVolume = notificationVolume;
    }

    public void setRingtoneVolume(int ringtoneVolume) {
        this.ringtoneVolume = ringtoneVolume;
    }

    public void setSystemVolume(int systemVolume) {
        this.systemVolume = systemVolume;
    }

    public void setWifiState(boolean wifiState) {
        this.wifiState = wifiState;
    }

    public void setBluetoothState(boolean bluetoothState) {
        this.bluetoothState = bluetoothState;
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
        dest.writeByte((byte) (wifiState ? 1 : 0));
        dest.writeByte((byte) (bluetoothState ? 1 : 0));
        dest.writeInt(startMoment.get(Calendar.YEAR));
        dest.writeInt(startMoment.get(Calendar.MONTH));
        dest.writeInt(startMoment.get(Calendar.DAY_OF_MONTH));
        dest.writeInt(startMoment.get(Calendar.HOUR_OF_DAY));
        dest.writeInt(startMoment.get(Calendar.MINUTE));
        dest.writeInt(startMoment.get(Calendar.SECOND));
        dest.writeInt(startMoment.get(Calendar.MILLISECOND));
    }

    @Override
    public void run() {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarmVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, ringtoneVolume, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, systemVolume, 0);

        wifiManager.setWifiEnabled(wifiState);

        if (bluetoothState)
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    public void schedule() {
        long waitUntilRun = startMoment.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(this, waitUntilRun);
    }
}
