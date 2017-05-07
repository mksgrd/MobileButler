package com.maksg.mobilebutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import com.maksg.mobilebutler.fragments.ScheduleTaskFragment;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;
import es.dmoral.toasty.Toasty;

public class ScheduleTaskActivity extends AppCompatActivity {
    private ScheduleTaskFragment scheduleTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_task);

        initToolBar();
        initScheduleTaskFragment();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onScheduleTaskButtonClick(View view) {
        try {
            SettingsChangeTask settingsChangeTask = scheduleTaskFragment.getSettingsChangeTask();
            Intent intent = new Intent();
            intent.putExtra("Task", settingsChangeTask);
            setResult(RESULT_OK, intent);

            Toasty.success(this, "Задача успешно запланирована!", Toast.LENGTH_LONG, true).show();

            finish();
        } catch (RuntimeException e) {
            Toasty.error(this, "Дата и(или) время указаны некорректно",
                    Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.scheduleTaskToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Планирование задачи");
    }

    private void initScheduleTaskFragment() {
        scheduleTaskFragment = (ScheduleTaskFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        scheduleTaskFragment.setEditTextViewText("Имя задачи:");
        scheduleTaskFragment.setEditTextText("Новая задача");
        scheduleTaskFragment.setRunDateTimeTextViewText("Выбранная дата и время:");
        scheduleTaskFragment.setSettingsChangeTask(new SettingsChangeTask());
    }
}