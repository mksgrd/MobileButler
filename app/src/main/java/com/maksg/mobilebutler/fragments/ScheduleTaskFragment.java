package com.maksg.mobilebutler.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;

import java.util.Calendar;

public class ScheduleTaskFragment extends Fragment implements View.OnClickListener {
    private SettingsChangeTask settingsChangeTask;
    private View view;
    private Calendar runDateTime = Calendar.getInstance();
    private TextView editTextView, settingsInfoTextView, alarmTextView, musicTextView, notificationTextView,
            ringtoneTextView, systemTextView, selectedDateTime;
    private EditText editText;
    private SeekBar alarmSeekBar, musicSeekBar, notificationSeekBar, ringtoneSeekBar, systemSeekBar;
    private Switch disableAllSoundsSwitch, wifiSwitch, bluetoothSwitch;
    private Button pointDateButton, pointTimeButton;
    private String runDateTimeTextViewText;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.alarmSeekBar:
                    alarmTextView.setText(String.format("Громкость предупреждений: %s", Integer.toString(progress)));
                    break;
                case R.id.musicSeekBar:
                    musicTextView.setText(String.format("Громкость музыки: %s", Integer.toString(progress)));
                    break;
                case R.id.notificationSeekBar:
                    notificationTextView.setText(String.format("Громкость оповещений: %s", Integer.toString(progress)));
                    break;
                case R.id.ringtoneSeekBar:
                    ringtoneTextView.setText(String.format("Громкость мелодии звонка: %s", Integer.toString(progress)));
                    break;
                case R.id.systemSeekBar:
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pointDateButton:
                showDatePickerDialog();
                break;
            case R.id.pointTimeButton:
                showTimePickerDialog();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule_task, container, false);

        initTextViews();
        initEditText();
        initSeekBars();
        initSwitches();
        initButtons();

        return view;
    }

    public void setEditTextViewText(String text) {
        editTextView.setText(text);
    }

    public void setEditTextText(String text) {
        editText.setText(text);
    }

    public void setRunDateTimeTextViewText(String text) {
        this.runDateTimeTextViewText = text;
        updateRunDateTimeTextView();
    }

    public SettingsChangeTask getSettingsChangeTask() throws RuntimeException {
        if (Calendar.getInstance().getTimeInMillis() >= runDateTime.getTimeInMillis())
            throw new RuntimeException("Incorrect schedule time");

        if (disableAllSoundsSwitch.isChecked()) {
            alarmSeekBar.setProgress(0);
            musicSeekBar.setProgress(0);
            notificationSeekBar.setProgress(0);
            ringtoneSeekBar.setProgress(0);
            systemSeekBar.setProgress(0);
        }

        settingsChangeTask.setSettings(SettingsChangeTask.ALARM_VOLUME, alarmSeekBar.getProgress());
        settingsChangeTask.setSettings(SettingsChangeTask.MUSIC_VOLUME, musicSeekBar.getProgress());
        settingsChangeTask.setSettings(SettingsChangeTask.NOTIFICATION_VOLUME, notificationSeekBar.getProgress());
        settingsChangeTask.setSettings(SettingsChangeTask.RINGTONE_VOLUME, ringtoneSeekBar.getProgress());
        settingsChangeTask.setSettings(SettingsChangeTask.SYSTEM_VOLUME, systemSeekBar.getProgress());

        settingsChangeTask.setSettings(SettingsChangeTask.WIFI_STATE, wifiSwitch.isChecked() ? 1 : 0);
        settingsChangeTask.setSettings(SettingsChangeTask.BLUETOOTH_STATE, bluetoothSwitch.isChecked() ? 1 : 0);

        settingsChangeTask.setRunDateTime(runDateTime);

        settingsChangeTask.setName(editText.getText().toString());

        return settingsChangeTask;
    }

    public void setSettingsChangeTask(SettingsChangeTask settingsChangeTask) {
        this.settingsChangeTask = settingsChangeTask;
    }

    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                runDateTime.set(Calendar.YEAR, year);
                runDateTime.set(Calendar.MONTH, month);
                runDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateRunDateTimeTextView();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), onDateSetListener,
                runDateTime.get(Calendar.YEAR), runDateTime.get(Calendar.MONTH),
                runDateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                runDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                runDateTime.set(Calendar.MINUTE, minute);
                updateRunDateTimeTextView();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener,
                runDateTime.get(Calendar.HOUR_OF_DAY), runDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public void updateRunDateTimeTextView() {
        selectedDateTime.setText(String.format(runDateTimeTextViewText + "\n%s", DateUtils.formatDateTime(getContext(),
                runDateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));
    }

    private void initTextViews() {
        editTextView = (TextView) view.findViewById(R.id.editTextView);
        settingsInfoTextView = (TextView) view.findViewById(R.id.infoTextView);
        alarmTextView = (TextView) view.findViewById(R.id.alarmTextView);
        musicTextView = (TextView) view.findViewById(R.id.musicTextView);
        notificationTextView = (TextView) view.findViewById(R.id.notificationTextView);
        ringtoneTextView = (TextView) view.findViewById(R.id.ringtoneTextView);
        systemTextView = (TextView) view.findViewById(R.id.systemTextView);
        selectedDateTime = (TextView) view.findViewById(R.id.selectedDateTime);
    }

    private void initEditText() {
        editText = (EditText) view.findViewById(R.id.editText);
    }

    private void initSeekBars() {
        alarmSeekBar = (SeekBar) view.findViewById(R.id.alarmSeekBar);
        musicSeekBar = (SeekBar) view.findViewById(R.id.musicSeekBar);
        notificationSeekBar = (SeekBar) view.findViewById(R.id.notificationSeekBar);
        ringtoneSeekBar = (SeekBar) view.findViewById(R.id.ringtoneSeekBar);
        systemSeekBar = (SeekBar) view.findViewById(R.id.systemSeekBar);
        setUpSeekBars();
    }

    private void setUpSeekBars() {
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

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
        disableAllSoundsSwitch = (Switch) view.findViewById(R.id.disableAllSoundsSwitch);
        wifiSwitch = (Switch) view.findViewById(R.id.wifiSwitch);
        bluetoothSwitch = (Switch) view.findViewById(R.id.bluetoothSwitch);
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

        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setChecked(wifiManager.isWifiEnabled());

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothSwitch.setChecked(bluetoothAdapter.isEnabled());
    }

    private void initButtons() {
        pointDateButton = (Button) view.findViewById(R.id.pointDateButton);
        pointDateButton.setOnClickListener(this);

        pointTimeButton = (Button) view.findViewById(R.id.pointTimeButton);
        pointTimeButton.setOnClickListener(this);
    }
}
