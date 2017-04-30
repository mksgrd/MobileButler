package com.maksg.mobilebutler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.maksg.mobilebutler.R;
import com.maksg.mobilebutler.TaskDTO;
import com.maksg.mobilebutler.adapters.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class TasksTabFragment extends TabFragment {

    public static TasksTabFragment getInstance(Context context) {
        Bundle args = new Bundle();
        TasksTabFragment fragment = new TasksTabFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_tasks));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_tab, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new TaskAdapter(testMock()));

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<TaskDTO> testMock() {
        List<TaskDTO> list = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            list.add(new TaskDTO("Title" + Integer.toString(i), "time" + Integer.toString(i)));
        return list;
    }
}