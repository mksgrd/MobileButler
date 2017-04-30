package com.maksg.mobilebutler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.maksg.mobilebutler.R;

public class EventsTabFragment extends TabFragment {
    public static EventsTabFragment getInstance(Context context) {
        Bundle args = new Bundle();
        EventsTabFragment fragment = new EventsTabFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_events));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events_tab, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}