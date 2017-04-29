package com.maksg.mobilebutler.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import com.maksg.mobilebutler.fragments.AbstractTabFragment;
import com.maksg.mobilebutler.fragments.EventsTabFragment;
import com.maksg.mobilebutler.fragments.ProfileTabFragment;
import com.maksg.mobilebutler.fragments.TasksTabFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<AbstractTabFragment> tabs = new SparseArray<>();
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
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

    private void initTabsMap(Context context) {
        tabs.put(0, TasksTabFragment.getInstance(context));
        tabs.put(1, EventsTabFragment.getInstance(context));
        tabs.put(2, ProfileTabFragment.getInstance(context));
    }
}