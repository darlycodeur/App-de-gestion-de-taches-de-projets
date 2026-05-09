package com.taskflow.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.taskflow.app.R;
import com.taskflow.app.database.model.ProjectProgress;

import java.util.ArrayList;
import java.util.List;

public class ProjectProgressAdapter extends RecyclerView.Adapter<ProjectProgressAdapter.ViewHolder> {

    private List<ProjectProgress> projects = new ArrayList<>();
    private OnProjectClickListener listener;

    public interface OnProjectClickListener {
        void onProjectClick(ProjectProgress project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(projects.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<ProjectProgress> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProjectName;
        LinearProgressIndicator progressIndicator;
        TextView tvProgressText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tv_project_name);
            progressIndicator = itemView.findViewById(R.id.progress_indicator);
            tvProgressText = itemView.findViewById(R.id.tv_progress_text);
        }

        void bind(ProjectProgress project, OnProjectClickListener listener) {
            tvProjectName.setText(project.getProjectName());
            progressIndicator.setProgress(project.getProgress());
            tvProgressText.setText(project.getCompletedTasks() + "/" + project.getTotalTasks());

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onProjectClick(project);
            });
        }
    }
}
