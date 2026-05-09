package com.taskflow.app.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.taskflow.app.ui.fragment.TaskListFragment;

public class TaskTabsAdapter extends FragmentStateAdapter {

    private int projectId;

    public TaskTabsAdapter(@NonNull FragmentActivity fragmentActivity, int projectId) {
        super(fragmentActivity);
        this.projectId = projectId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TaskListFragment.newInstance(projectId);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
