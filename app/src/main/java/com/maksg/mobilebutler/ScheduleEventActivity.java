package com.maksg.mobilebutler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ScheduleEventActivity extends AppCompatActivity {
    private Calendar currentDateTime = Calendar.getInstance();
    private TextView selectedTime, selectedDate, selectedAction;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        }
    };
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);

        selectedTime = (TextView) findViewById(R.id.selectedTimeTextView);
        selectedDate = (TextView) findViewById(R.id.selectedDateTextView);
        selectedAction = (TextView) findViewById(R.id.selectedActionTextView);

        String formattedTime = "Выбранное время:\n" +
                DateUtils.formatDateTime(this, currentDateTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);

        selectedTime.setText(formattedTime);

        String formattedDate = "Выбранная дата:\n" +
                DateUtils.formatDateTime(this, currentDateTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);

        selectedDate.setText(formattedDate);
    }

    public void onPointTimeButtonClick(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                currentDateTime.get(Calendar.HOUR_OF_DAY), currentDateTime.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Выберите желаемое время");
        timePickerDialog.show();
    }

    public void onPointDateButtonClick(View view) {
    }

    public void onPointActionButtonClick(View view) {
    }

    public void onCreateEventButtonClick(View view) {
    }
}
