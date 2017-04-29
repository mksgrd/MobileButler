package com.maksg.mobilebutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.maksg.mobilebutler.R;

public class ExampleFragment extends Fragment {
    private View view;

    public ExampleFragment() {
        Bundle bundle = new Bundle();
        setArguments(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_example, container, false);
        return view;
    }
}
