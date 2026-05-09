package com.taskflow.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.taskflow.app.R;
import com.taskflow.app.database.model.ProjectWithTacheCount;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private List<ProjectWithTacheCount> projects = new ArrayList<>();
    private OnProjectClickListener listener;
    private OnProjectLongClickListener longClickListener;

    public interface OnProjectClickListener {
        void onProjectClick(ProjectWithTacheCount project);
    }

    public interface OnProjectLongClickListener {
        void onProjectLongClick(ProjectWithTacheCount project);
    }

    public void setOnProjectClickListener(OnProjectClickListener listener) {
        this.listener = listener;
    }

    public void setOnProjectLongClickListener(OnProjectLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(projects.get(position), listener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<ProjectWithTacheCount> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    public ProjectWithTacheCount getProjectAt(int position) {
        return projects.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProjectName;
        TextView tvProjectDescription;
        TextView tvTaskCount;
        LinearProgressIndicator progressIndicator;
        TextView tvProgressPercentage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tv_project_name);
            tvProjectDescription = itemView.findViewById(R.id.tv_project_description);
            tvTaskCount = itemView.findViewById(R.id.tv_task_count);
            progressIndicator = itemView.findViewById(R.id.progress_indicator);
            tvProgressPercentage = itemView.findViewById(R.id.tv_progress_percentage);
        }

        void bind(ProjectWithTacheCount project, OnProjectClickListener listener,
                OnProjectLongClickListener longClickListener) {
            tvProjectName.setText(project.project.getNom());
            tvProjectDescription.setText(project.project.getDescription());
            tvTaskCount.setText(project.tacheCount + " tâche" + (project.tacheCount > 1 ? "s" : ""));

            int progress = project.getProgress();
            progressIndicator.setProgress(progress);
            tvProgressPercentage.setText(project.completedCount + "/" + project.tacheCount + " terminées");

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onProjectClick(project);
            });
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) longClickListener.onProjectLongClick(project);
                return true;
            });
        }
    }
}
