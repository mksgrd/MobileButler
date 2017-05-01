package com.maksg.mobilebutler;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ChooseDateTimeActivity extends AppCompatActivity {
    private final Calendar dateTime = Calendar.getInstance();
    private SettingsChangeTask settingsChangeTask;
    private TextView selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_time);

        settingsChangeTask = getIntent().getParcelableExtra("Task");
        selectedDateTime = (TextView) findViewById(R.id.textView5);
        updateSelectedDateTimeTextView();
    }

    private void updateSelectedDateTimeTextView() {
        selectedDateTime.setText(String.format("Выбранная дата и время:\n%s", DateUtils.formatDateTime(this,
                dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));
    }

    public void onPointDateButtonClick(View view) {

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

    public void onChooseDateTimeNextButtonClick(View view) {
        dateTime.set(Calendar.SECOND, 0);
        dateTime.set(Calendar.MILLISECOND, 0);
        settingsChangeTask.setStartMoment(dateTime);

        Intent intent = new Intent(this, ScheduleTaskActivity.class);
        intent.putExtra("Task", settingsChangeTask);
        startActivity(intent);
    }
}
