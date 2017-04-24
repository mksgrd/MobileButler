package scheduler;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.SparseIntArray;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsChangeTask extends TimerTask implements Serializable {
    public static final int ALARM_VOLUME = 0;
    public static final int MUSIC_VOLUME = 1;
    public static final int NOTIFICATION_VOLUME = 2;
    public static final int RINGTONE_VOLUME = 3;
    public static final int SYSTEM_VOLUME = 4;
    public static final int WIFI_STATE = 5;
    public static final int BLUETOOTH_STATE = 6;

    private Calendar startMoment = Calendar.getInstance();
    private Timer timer = new Timer();
    private SparseIntArray settings = new SparseIntArray();
    private AudioManager audioManager;
    private WifiManager wifiManager;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public SettingsChangeTask(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void run() {
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, settings.get(ALARM_VOLUME), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, settings.get(MUSIC_VOLUME), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, settings.get(NOTIFICATION_VOLUME), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, settings.get(RINGTONE_VOLUME), 0);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, settings.get(SYSTEM_VOLUME), 0);

        wifiManager.setWifiEnabled(settings.get(WIFI_STATE) == 1);

        if (settings.get(BLUETOOTH_STATE) == 1)
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    public void setStartMoment(Calendar startMoment) {
        this.startMoment = startMoment;
    }

    public void putSettings(int key, int value) {
        settings.put(key, value);
    }

    public void schedule() {
        long waitUntilRun = startMoment.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        timer.schedule(this, waitUntilRun);
    }
}
