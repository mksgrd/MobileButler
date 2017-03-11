package com.maksg.mobilebutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseActionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
    }

    public void onChangeSoundSettingsButtonClick(View view) {
        Intent intent = new Intent(this, ChangeSoundSettingsActivity.class);
        startActivity(intent);
    }
}
