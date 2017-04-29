package com.maksg.mobilebutler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.maksg.mobilebutler.fragment.ExampleFragment;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

        tabs = new String[]{
                "Задачи",
                "События",
                "Профиль"
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ExampleFragment();
            case 1:
                return new ExampleFragment();
            case 2:
                return new ExampleFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
