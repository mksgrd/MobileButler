package com.maksg.mobilebutler.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import com.maksg.mobilebutler.fragments.TabFragment;
import com.maksg.mobilebutler.fragments.TasksTabFragment;
import com.maksg.mobilebutler.schedulables.SettingsChangeTask;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<TabFragment> tabs = new SparseArray<>();
    private TasksTabFragment tasksTabFragment, eventsTabFragment;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    public void addTask(SettingsChangeTask settingsChangeTask, int id) {
        switch (id) {
            case 0:
                tasksTabFragment.addTask(settingsChangeTask);
                break;
            case 1:
                eventsTabFragment.addTask(settingsChangeTask);
                break;
        }
    }

    private void initTabsMap(Context context) {
        tasksTabFragment = TasksTabFragment.getInstance(context, "Запланируйте\nновую задачу,\nнажав на плюс.", "Задачи");
        eventsTabFragment = TasksTabFragment.getInstance(context, "Запланируйте\nновое событие,\nнажав на плюс.", "События");
        tabs.put(0, tasksTabFragment);
        tabs.put(1, eventsTabFragment);
    }
}