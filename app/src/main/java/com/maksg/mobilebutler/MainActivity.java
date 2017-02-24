package com.maksg.mobilebutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onScheduleButtonClick(View view) {
        Intent intent = new Intent(this, ScheduleEventActivity.class);
        startActivity(intent);
    }

    public void onTaskButtonClick(View view) {
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
