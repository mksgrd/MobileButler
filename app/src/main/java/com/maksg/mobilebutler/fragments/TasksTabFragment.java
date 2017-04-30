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
import com.maksg.mobilebutler.adapters.TaskAdapter;
import com.maksg.mobilebutler.scheduler.SettingsChangeTask;

import java.util.Calendar;

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
        TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        SettingsChangeTask task = new SettingsChangeTask();
        task.setName("Новая задача 1");
        task.setStartMoment(Calendar.getInstance());
        taskAdapter.addTask(task);

        SettingsChangeTask task1 = new SettingsChangeTask();
        task1.setName("Новая задача 2");
        task1.setStartMoment(Calendar.getInstance());
        taskAdapter.addTask(task1);


        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}