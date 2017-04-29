package com.maksg.mobilebutler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.maksg.mobilebutler.R;

public class ProfileTabFragment extends AbstractTabFragment {
    public static ProfileTabFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ProfileTabFragment fragment = new ProfileTabFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_profile));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}