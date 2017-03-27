package com.maksg.mobilebutler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

public class ChangeSoundSettingsActivity extends AppCompatActivity {
    private TextView alarmTextView, musicTextView, notificationTextView, ringtoneTextView, systemTextView;
    private SeekBar alarmSeekBar, musicSeekBar, notificationSeekBar, ringtoneSeekBar, systemSeekBar;

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
        setContentView(R.layout.activity_change_sound_settings);

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
        musicSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        notificationSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        ringtoneSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        systemSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}