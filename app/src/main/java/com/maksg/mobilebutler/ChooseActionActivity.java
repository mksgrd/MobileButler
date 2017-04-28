package com.maksg.mobilebutler;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import scheduler.SettingsChangeTask;

public class ChooseActionActivity extends AppCompatActivity {
    private SettingsChangeTask settingsChangeTask;

    private TextView alarmTextView, musicTextView, notificationTextView, ringtoneTextView, systemTextView;
    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.activity_choose_action_alarmSeekBar:
                    alarmTextView.setText(String.format("Громкость предупреждений: %s", Integer.toString(progress)));
                    break;
                case R.id.activity_choose_action_musicSeekBar:
                    musicTextView.setText(String.format("Громкость музыки: %s", Integer.toString(progress)));
                    break;
                case R.id.activity_choose_action_notificationSeekBar:
                    notificationTextView.setText(String.format("Громкость оповещений: %s", Integer.toString(progress)));
                    break;
                case R.id.activity_choose_action_ringtoneSeekBar:
                    ringtoneTextView.setText(String.format("Громкость мелодии звонка: %s", Integer.toString(progress)));
                    break;
                case R.id.activity_choose_action_systemSeekBar:
                    systemTextView.setText(String.format("Громкость системных звуков: %s", Integer.toString(progress)));
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
    private SeekBar alarmSeekBar, musicSeekBar, notificationSeekBar, ringtoneSeekBar, systemSeekBar;
    private Switch disableAllSoundsSwitch, wifiSwitch, bluetoothSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        settingsChangeTask = getIntent().getParcelableExtra("Task");

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        alarmTextView = (TextView) findViewById(R.id.activity_choose_action_alarmTextView);
        musicTextView = (TextView) findViewById(R.id.activity_choose_action_musicTextView);
        notificationTextView = (TextView) findViewById(R.id.activity_choose_action_notificationTextView);
        ringtoneTextView = (TextView) findViewById(R.id.activity_choose_action_ringtoneTextView);
        systemTextView = (TextView) findViewById(R.id.activity_choose_action_systemTextView);

        alarmSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_alarmSeekBar);
        alarmSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        alarmSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        alarmSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));

        musicSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_musicSeekBar);
        musicSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        musicSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        musicSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        notificationSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_notificationSeekBar);
        notificationSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        notificationSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        notificationSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));

        ringtoneSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_ringtoneSeekBar);
        ringtoneSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        ringtoneSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        ringtoneSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        systemSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_systemSeekBar);
        systemSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        systemSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        systemSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));

        disableAllSoundsSwitch = (Switch) findViewById(R.id.activity_choose_action_disableAllSoundsSwitch);
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
        wifiSwitch = (Switch) findViewById(R.id.activity_choose_action_wifiSwitch);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothSwitch = (Switch) findViewById(R.id.activity_choose_action_bluetoothSwitch);
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
    }

    public void onApplySettingsButtonClicked(View view) {
        if (disableAllSoundsSwitch.isChecked()) {
            alarmSeekBar.setProgress(0);
            musicSeekBar.setProgress(0);
            notificationSeekBar.setProgress(0);
            ringtoneSeekBar.setProgress(0);
            systemSeekBar.setProgress(0);
        }

        settingsChangeTask.setAlarmVolume(alarmSeekBar.getProgress());
        settingsChangeTask.setMusicVolume(musicSeekBar.getProgress());
        settingsChangeTask.setNotificationVolume(notificationSeekBar.getProgress());
        settingsChangeTask.setRingtoneVolume(ringtoneSeekBar.getProgress());
        settingsChangeTask.setSystemVolume(systemSeekBar.getProgress());
        settingsChangeTask.setWifiEnabled(wifiSwitch.isChecked());
        settingsChangeTask.setBluetoothEnabled(bluetoothSwitch.isChecked());

        Intent intent = new Intent(this, ChooseDateTimeActivity.class);
        intent.putExtra("Task", settingsChangeTask);
        startActivity(intent);
    }
}
