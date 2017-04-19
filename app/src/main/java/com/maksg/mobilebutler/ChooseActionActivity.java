package com.maksg.mobilebutler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import deviceFunctionsManager.AudioController;
import deviceFunctionsManager.BluetoothController;
import deviceFunctionsManager.WifiController;

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

        AudioController audioController = new AudioController();
        audioController.setContext(this);

        WifiController wifiController = new WifiController();
        wifiController.setContext(this);

        BluetoothController bluetoothController = new BluetoothController();

        alarmTextView = (TextView) findViewById(R.id.alarmTextView);
        musicTextView = (TextView) findViewById(R.id.musicTextView);
        notificationTextView = (TextView) findViewById(R.id.notificationTextView);
        ringtoneTextView = (TextView) findViewById(R.id.ringtoneTextView);
        systemTextView = (TextView) findViewById(R.id.systemTextView);

        alarmSeekBar = (SeekBar) findViewById(R.id.alarmSeekBar);
        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar);
        notificationSeekBar = (SeekBar) findViewById(R.id.notificationSeekBar);
        ringtoneSeekBar = (SeekBar) findViewById(R.id.ringtoneSeekBar);
        systemSeekBar = (SeekBar) findViewById(R.id.systemSeekBar);

        alarmSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        alarmSeekBar.setProgress(audioController.getAlarmVolume());
        alarmSeekBar.setMax(audioController.getAlarmMaxVolume());

        musicSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        musicSeekBar.setProgress(audioController.getMusicVolume());
        musicSeekBar.setMax(audioController.getMusicMaxVolume());

        notificationSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        notificationSeekBar.setProgress(audioController.getNotificationVolume());
        notificationSeekBar.setMax(audioController.getNotificationMaxVolume());

        ringtoneSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        ringtoneSeekBar.setProgress(audioController.getRingtoneVolume());
        ringtoneSeekBar.setMax(audioController.getRingtoneMaxVolume());

        systemSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        systemSeekBar.setProgress(audioController.getSystemVolume());
        systemSeekBar.setMax(audioController.getSystemMaxVolume());

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

        wifiSwitch = (Switch) findViewById(R.id.wifiSwitch);
        wifiSwitch.setChecked(wifiController.isWifiEnabled());

        bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);
        bluetoothSwitch.setChecked(bluetoothController.getBluetoothState());
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
