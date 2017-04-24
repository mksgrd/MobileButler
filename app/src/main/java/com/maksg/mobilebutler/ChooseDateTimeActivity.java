package com.maksg.mobilebutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import scheduler.SettingsChangeTask;

import java.util.Calendar;

public class ChooseDateTimeActivity extends AppCompatActivity {
    private SettingsChangeTask settingsChangeTask;

    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date_time);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        settingsChangeTask = (SettingsChangeTask) getIntent().getSerializableExtra("Task");
    }

    public void onChooseDateTimeNextButtonClick(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        settingsChangeTask.setStartMoment(calendar);

        Intent intent = new Intent(this, ScheduleEventActivity.class);
        intent.putExtra("Task", settingsChangeTask);
        startActivity(intent);
    }
}
