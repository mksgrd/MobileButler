package com.maksg.mobilebutler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.TextView;
import scheduler.SettingsChangeTask;

public class ScheduleEventActivity extends AppCompatActivity {
    private SettingsChangeTask settingsChangeTask;
    private TextView dateTimeTextView, settingsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        settingsChangeTask = getIntent().getParcelableExtra("Task");

        dateTimeTextView = (TextView) findViewById(R.id.dateTimeTextView);
        dateTimeTextView.setText(String.format("Выбранное дата и время:\n%s", DateUtils.formatDateTime(this,
                settingsChangeTask.getStartMoment().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));

        settingsTextView = (TextView) findViewById(R.id.settingsTextView);
    }
}