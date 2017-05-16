package com.maksg.mobilebutler.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.fragments.ScheduleTaskFragment;
import com.maksg.mobilebutler.schedulables.SettingsChangeEvent;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;
import es.dmoral.toasty.Toasty;

import java.util.Calendar;

public class ScheduleEventActivity extends AppCompatActivity {

    private ScheduleTaskFragment scheduleTaskFragment;
    private TextView selectedEndDateTime;
    private Calendar endDateTime = Calendar.getInstance();
    private boolean onPrepareOptionsMenuInvoked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        initToolBar();
        initTextView();
        initScheduleTaskFragment();

        endDateTime.add(Calendar.MINUTE, 2);

        updateEndDateTimeTextView();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!onPrepareOptionsMenuInvoked) {
            onPrepareOptionsMenuInvoked = true;
            getMenuInflater().inflate(R.menu.menu, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onPointEndDateButtonClick(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endDateTime.set(Calendar.YEAR, year);
                endDateTime.set(Calendar.MONTH, month);
                endDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDateTimeTextView();
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener,
                endDateTime.get(Calendar.YEAR), endDateTime.get(Calendar.MONTH),
                endDateTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onPointEndTimeButtonClick(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endDateTime.set(Calendar.MINUTE, minute);
                updateEndDateTimeTextView();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                endDateTime.get(Calendar.HOUR_OF_DAY), endDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public void onScheduleEventButtonClick(View view) {
        SettingsChangeEvent settingsChangeEvent = new SettingsChangeEvent(scheduleTaskFragment.getSettingsChangeTask());
        settingsChangeEvent.setRestoreDateTime(endDateTime);

        if (settingsChangeEvent.getRunDateTime().getTimeInMillis() <= Calendar.getInstance().getTimeInMillis() ||
                settingsChangeEvent.getRunDateTime().getTimeInMillis() >= endDateTime.getTimeInMillis()) {
            Toasty.error(this, "Дата и(или) время указаны некорректно",
                    Toast.LENGTH_SHORT, true).show();
            return;
        }

        settingsChangeEvent.setContext(this);
        settingsChangeEvent.saveCurrentSettings();

        Intent intent = new Intent();
        intent.putExtra("Task", settingsChangeEvent);
        setResult(RESULT_OK, intent);

        Toasty.success(this, "Событие успешно запланировано!", Toast.LENGTH_LONG, true).show();

        finish();
    }

    private void updateEndDateTimeTextView() {
        selectedEndDateTime.setText(String.format("Дата и время окончания события:\n%s", DateUtils.formatDateTime(
                this, endDateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME |
                        DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR)));
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.scheduleEventToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Планирование события");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getBaseContext(), HelpActivity.class));
                return true;
            }
        });
    }

    private void initTextView() {
        selectedEndDateTime = (TextView) findViewById(R.id.selectedEndDateTime);
    }

    private void initScheduleTaskFragment() {
        scheduleTaskFragment = (ScheduleTaskFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        scheduleTaskFragment.setEditTextViewText("Имя события:");
        scheduleTaskFragment.setEditTextText("Новое событие");
        scheduleTaskFragment.setRunDateTimeTextViewText("Дата и время начала события:");
        scheduleTaskFragment.setSettingsChangeTask(new SettingsChangeTask());
    }
}
