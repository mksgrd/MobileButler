package com.maksg.mobilebutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import scheduler.SettingsChangeTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onScheduleEventButtonClick(View view) {
        SettingsChangeTask settingsChangeTask = new SettingsChangeTask();
        Intent intent = new Intent(this, ChooseActionActivity.class);
        intent.putExtra("Task", settingsChangeTask);
        startActivity(intent);
    }

    public void onScheduleTaskButtonClick(View view) {
        Intent intent = new Intent(this, ScheduleTaskActivity.class);
        startActivity(intent);
    }

    public void onCurrentProfileButtonClick(View view) {
        Intent intent = new Intent(this, CurrentProfileActivity.class);
        startActivity(intent);
    }

    public void onConfigureProfileButtonClick(View view) {
        Intent intent = new Intent(this, CurrentProfileActivity.class);
        startActivity(intent);
    }
}
