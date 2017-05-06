package com.maksg.mobilebutler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.maksg.mobilebutler.R;

public class ScheduleTaskFragment extends Fragment {
    private View view;
    private TextView editTextView, settingsInfoTextView, alarmTextView, musicTextView, notificationTextView,
            ringtoneTextView, systemTextView, selectedDateTime;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule_task, container, false);

        initTextViews();
        initEditText();
        initSeekBars();
        initSwitches();

        return view;
    }

    public void setUpEditTextField(String textViewData, String editTextData) {
        editTextView.setText(textViewData);
        editText.setText(editTextData);
    }

    public void setUpSettingsInfoField(String settingsInfo) {

    }

    public void setUpSelectedDateTimeField(String selectedDateTime) {

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
        setUpSeekBars();
    }

    private void setUpSeekBars() {

    }

    private void initSwitches() {
        setUpSwitches();
    }

    private void setUpSwitches() {

    }
}
