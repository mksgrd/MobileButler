package com.maksg.mobilebutler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;
import scheduler.Event;
import scheduler.EventScheduler;

import java.util.Calendar;

public class ScheduleEventActivity extends AppCompatActivity {
    private Calendar dateTime = Calendar.getInstance();
    private TextView selectedTime, selectedDate;
    private Spinner spinner;
    private EventScheduler eventScheduler = new EventScheduler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        selectedTime = (TextView) findViewById(R.id.selectedTimeTextView);
        selectedDate = (TextView) findViewById(R.id.selectedDateTextView);
        updateDateTimeTextViews();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spinner.setEnabled(isChecked);
            }
        });
    }

    private void updateDateTimeTextViews() {
        String formattedTime = "Выбранное время:\n" +
                DateUtils.formatDateTime(this, dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        selectedTime.setText(formattedTime);

        String formattedDate = "Выбранная дата:\n" +
                DateUtils.formatDateTime(this, dateTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        selectedDate.setText(formattedDate);
    }

    public void onPointTimeButtonClick(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateTime.set(Calendar.MINUTE, minute);
                dateTime.set(Calendar.SECOND, 0);
                dateTime.set(Calendar.MILLISECOND, 0);
                updateDateTimeTextViews();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Укажите желаемое время");
        timePickerDialog.show();
    }

    public void onPointDateButtonClick(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateTime.set(Calendar.YEAR, year);
                dateTime.set(Calendar.MONTH, month);
                dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateTimeTextViews();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Укажите желаемую дату");
        datePickerDialog.show();
    }

    public void onPointActionButtonClick(View view) {
        Intent intent = new Intent(this, ChooseActionActivity.class);
        startActivity(intent);
    }

    public void onCreateEventButtonClick(View view) {
        final SharedPreferences sharedPreferences = getSharedPreferences("MobileButlerSP", MODE_PRIVATE);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Event event = new Event() {
            @Override
            public void run() {
                audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                        sharedPreferences.getInt("alarm_volume", 1), 0);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        sharedPreferences.getInt("music_volume", 1), 0);

                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,
                        sharedPreferences.getInt("notification_volume", 1), 0);

                audioManager.setStreamVolume(AudioManager.STREAM_RING,
                        sharedPreferences.getInt("ringtone_volume", 1), 0);

                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,
                        sharedPreferences.getInt("system_volume", 1), 0);

                wifiManager.setWifiEnabled(sharedPreferences.getBoolean("wifi_state", false));

                if (sharedPreferences.getBoolean("bluetooth_state", false))
                    bluetoothAdapter.enable();
                else
                    bluetoothAdapter.disable();
            }
        };

        event.setStartMoment(dateTime);
        eventScheduler.scheduleEvent(event);
    }
}
