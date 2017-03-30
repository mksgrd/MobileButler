package com.maksg.mobilebutler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

public class ScheduleEventActivity extends AppCompatActivity {
    private Calendar dateTime = Calendar.getInstance();
    private TextView selectedTime, selectedDate;
    private Spinner spinner;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateDateTimeTextViews();
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, month);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTimeTextViews();
        }
    };

    private CheckBox.OnCheckedChangeListener onCheckBoxCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            spinner.setEnabled(isChecked);
        }
    };

    private void updateDateTimeTextViews() {
        String formattedTime = "Выбранное время:\n" +
                DateUtils.formatDateTime(this, dateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        selectedTime.setText(formattedTime);

        String formattedDate = "Выбранная дата:\n" +
                DateUtils.formatDateTime(this, dateTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        selectedDate.setText(formattedDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        selectedTime = (TextView) findViewById(R.id.selectedTimeTextView);
        selectedDate = (TextView) findViewById(R.id.selectedDateTextView);
        updateDateTimeTextViews();

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(onCheckBoxCheckedListener);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setEnabled(false);
    }

    public void onPointTimeButtonClick(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Укажите желаемое время");
        timePickerDialog.show();
    }

    public void onPointDateButtonClick(View view) {
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
    }
}
