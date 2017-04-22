package com.maksg.mobilebutler;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ChooseActionActivity extends AppCompatActivity {
    private TextView alarmTextView, musicTextView, notificationTextView, ringtoneTextView, systemTextView;
    private SeekBar alarmSeekBar, musicSeekBar, notificationSeekBar, ringtoneSeekBar, systemSeekBar;

    private Switch disableAllSoundsSwitch, wifiSwitch, bluetoothSwitch;

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.alarmSeekBar:
                    alarmTextView.setText("Громкость предупреждений: " + Integer.toString(progress));
                    break;
                case R.id.musicSeekBar:
                    musicTextView.setText("Громкость музыки: " + Integer.toString(progress));
                    break;
                case R.id.notificationSeekBar:
                    notificationTextView.setText("Громкость оповещений: " + Integer.toString(progress));
                    break;
                case R.id.ringtoneSeekBar:
                    ringtoneTextView.setText("Громкость мелодии звонка: " + Integer.toString(progress));
                    break;
                case R.id.systemSeekBar:
                    systemTextView.setText("Громкость системных звуков: " + Integer.toString(progress));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        alarmTextView = (TextView) findViewById(R.id.alarmTextView);
        musicTextView = (TextView) findViewById(R.id.musicTextView);
        notificationTextView = (TextView) findViewById(R.id.notificationTextView);
        ringtoneTextView = (TextView) findViewById(R.id.ringtoneTextView);
        systemTextView = (TextView) findViewById(R.id.systemTextView);

        alarmSeekBar = (SeekBar) findViewById(R.id.alarmSeekBar);
        alarmSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        alarmSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        alarmSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));

        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);
        musicSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        musicSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        musicSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        notificationSeekBar = (SeekBar) findViewById(R.id.notificationSeekBar);
        notificationSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        notificationSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        notificationSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));

        ringtoneSeekBar = (SeekBar) findViewById(R.id.ringtoneSeekBar);
        ringtoneSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        ringtoneSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        ringtoneSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        systemSeekBar = (SeekBar) findViewById(R.id.systemSeekBar);
        systemSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        systemSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        systemSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));

        disableAllSoundsSwitch = (Switch) findViewById(R.id.disableAllSoundsSwitch);
        disableAllSoundsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmSeekBar.setEnabled(!isChecked);
                musicSeekBar.setEnabled(!isChecked);
                notificationSeekBar.setEnabled(!isChecked);
                ringtoneSeekBar.setEnabled(!isChecked);
                systemSeekBar.setEnabled(!isChecked);
            }
        });

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
    }

    public void onApplySettingsButtonClicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();

        if (disableAllSoundsSwitch.isChecked()) {
            editor.putInt("alarm_volume", 0);
            editor.putInt("music_volume", 0);
            editor.putInt("notification_volume", 0);
            editor.putInt("ringtone_volume", 0);
            editor.putInt("system_volume", 0);
        } else {
            editor.putInt("alarm_volume", alarmSeekBar.getProgress());
            editor.putInt("music_volume", musicSeekBar.getProgress());
            editor.putInt("notification_volume", notificationSeekBar.getProgress());
            editor.putInt("ringtone_volume", ringtoneSeekBar.getProgress());
            editor.putInt("system_volume", systemSeekBar.getProgress());
        }

        editor.putBoolean("wifi_state", wifiSwitch.isChecked());
        editor.putBoolean("bluetooth_state", bluetoothSwitch.isChecked());

        editor.apply();
        finish();
    }
}
