package com.maksg.mobilebutler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.adapters.TabsFragmentAdapter;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabsFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initTabs();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        SettingsChangeTask settingsChangeTask = data.getParcelableExtra("Task");
        settingsChangeTask.setContext(this);
        adapter.addTask(settingsChangeTask, viewPager.getCurrentItem());
    }

    public void onFloatingActionButtonClick(View view) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                startActivityForResult(new Intent(this, ScheduleTaskActivity.class), 1);
                break;
            case 1:
                startActivityForResult(new Intent(this, ScheduleEventActivity.class), 1);
                break;
            case 2:
                Toasty.info(this, "Профиль", Toast.LENGTH_SHORT, true).show();
                break;
        }
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
