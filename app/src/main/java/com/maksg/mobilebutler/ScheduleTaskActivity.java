package com.maksg.mobilebutler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import es.dmoral.toasty.Toasty;

import java.util.Calendar;

public class ScheduleTaskActivity extends AppCompatActivity {
    private SCTask SCTask = new SCTask();
    private Calendar dateTime = Calendar.getInstance();

    private TextView alarmTextView, musicTextView, notificationTextView, ringtoneTextView, systemTextView,
            selectedDateTime;
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
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_task);

        initToolBar();
        initTextViews();
        initSeekBars();
        initSwitches();
        initEditText();

        updateSelectedDateTimeTextView();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onScheduleTaskButtonClick(View view) {
        if (Calendar.getInstance().getTimeInMillis() >= dateTime.getTimeInMillis()) {
            Toasty.error(this, "Невозможно запланировать задачу на данное время и дату",
                    Toast.LENGTH_LONG, true).show();
            return;
        }

        if (disableAllSoundsSwitch.isChecked()) {
            alarmSeekBar.setProgress(0);
            musicSeekBar.setProgress(0);
            notificationSeekBar.setProgress(0);
            ringtoneSeekBar.setProgress(0);
            systemSeekBar.setProgress(0);
        }

        SCTask.setAlarmVolume(alarmSeekBar.getProgress());
        SCTask.setMusicVolume(musicSeekBar.getProgress());
        SCTask.setNotificationVolume(notificationSeekBar.getProgress());
        SCTask.setRingtoneVolume(ringtoneSeekBar.getProgress());
        SCTask.setSystemVolume(systemSeekBar.getProgress());
        SCTask.setWifiEnabled(wifiSwitch.isChecked());
        SCTask.setBluetoothEnabled(bluetoothSwitch.isChecked());

        SCTask.setStartMoment(dateTime);

        SCTask.setName(editText.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("Task", SCTask);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onPointTimeButtonClick(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateTime.set(Calendar.MINUTE, minute);
                updateSelectedDateTimeTextView();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public void onPointDateButtonClick(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateTime.set(Calendar.YEAR, year);
                dateTime.set(Calendar.MONTH, month);
                dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSelectedDateTimeTextView();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void updateSelectedDateTimeTextView() {
        selectedDateTime.setText(String.format("Выбранная дата и время:\n%s", DateUtils.formatDateTime(this,
                dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.scheduleTaskToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Планирование задачи");
    }

    private void initTextViews() {
        alarmTextView = (TextView) findViewById(R.id.activity_choose_action_alarmTextView);
        musicTextView = (TextView) findViewById(R.id.activity_choose_action_musicTextView);
        notificationTextView = (TextView) findViewById(R.id.activity_choose_action_notificationTextView);
        ringtoneTextView = (TextView) findViewById(R.id.activity_choose_action_ringtoneTextView);
        systemTextView = (TextView) findViewById(R.id.activity_choose_action_systemTextView);
        selectedDateTime = (TextView) findViewById(R.id.selectedDateTime);
    }

    private void initSeekBars() {
        alarmSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_alarmSeekBar);
        musicSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_musicSeekBar);
        notificationSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_notificationSeekBar);
        ringtoneSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_ringtoneSeekBar);
        systemSeekBar = (SeekBar) findViewById(R.id.activity_choose_action_systemSeekBar);
        setUpSeekBars();
    }

    private void setUpSeekBars() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        alarmSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        alarmSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        alarmSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));

        musicSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        musicSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        musicSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        notificationSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        notificationSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        notificationSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));

        ringtoneSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        ringtoneSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        ringtoneSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        systemSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        systemSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        systemSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
    }

    private void initSwitches() {
        disableAllSoundsSwitch = (Switch) findViewById(R.id.activity_choose_action_disableAllSoundsSwitch);
        wifiSwitch = (Switch) findViewById(R.id.activity_choose_action_wifiSwitch);
        bluetoothSwitch = (Switch) findViewById(R.id.activity_choose_action_bluetoothSwitch);
        setUpSwitches();
    }

    private void setUpSwitches() {
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
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
    }

    private void initEditText() {
        editText = (EditText) findViewById(R.id.editText);
    }
}