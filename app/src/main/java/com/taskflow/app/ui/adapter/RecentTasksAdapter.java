package com.taskflow.app.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.taskflow.app.R;
import com.taskflow.app.database.model.TaskWithProject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentTasksAdapter extends RecyclerView.Adapter<RecentTasksAdapter.ViewHolder> {

    private List<TaskWithProject> tasks = new ArrayList<>();
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(TaskWithProject item);
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<TaskWithProject> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View priorityDot;
        TextView tvTaskTitle;
        TextView tvProjectName;
        Chip chipStatus;
        TextView tvDueDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            priorityDot = itemView.findViewById(R.id.priority_dot);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            tvProjectName = itemView.findViewById(R.id.tv_project_name);
            chipStatus = itemView.findViewById(R.id.chip_status);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
        }

        void bind(TaskWithProject item, RecentTasksAdapter.OnTaskClickListener listener) {
            tvTaskTitle.setText(item.tache.getTitre());
            tvProjectName.setText(item.project != null ? item.project.getNom() : "Sans projet");

            int priorityColor = getPriorityColor(item.tache.getPriorite());
            GradientDrawable drawable = (GradientDrawable) priorityDot.getBackground();
            drawable.setColor(priorityColor);

            chipStatus.setText(item.tache.getStatut());
            setStatusChipColor(chipStatus, item.tache.getStatut());

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.FRANCE);
            String dateStr = sdf.format(new Date(item.tache.getDateEcheance()));

            boolean isOverdue = item.tache.getDateEcheance() < System.currentTimeMillis()
                    && !item.tache.getStatut().equals("terminée");
            tvDueDate.setText(dateStr);
            tvDueDate.setTextColor(isOverdue
                    ? Color.parseColor("#D32F2F")
                    : Color.parseColor("#757575"));

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onTaskClick(item);
            });
        }

        private int getPriorityColor(int priority) {
            switch (priority) {
                case 5: return Color.parseColor("#D32F2F");
                case 4: return Color.parseColor("#F57C00");
                case 3: return Color.parseColor("#F9A825");
                case 2: return Color.parseColor("#388E3C");
                default: return Color.parseColor("#757575");
            }
        }

        private void setStatusChipColor(Chip chip, String statut) {
            int bgColor, textColor;
            switch (statut) {
                case "en cours":
                    bgColor = Color.parseColor("#E3F2FD");
                    textColor = Color.parseColor("#1976D2");
                    break;
                case "terminée":
                    bgColor = Color.parseColor("#E8F5E9");
                    textColor = Color.parseColor("#388E3C");
                    break;
                default: // à faire
                    bgColor = Color.parseColor("#F5F5F5");
                    textColor = Color.parseColor("#757575");
                    break;
            }
            chip.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(bgColor));
            chip.setChipStrokeWidth(0f);
            chip.setTextColor(textColor);
        }
    }
}
