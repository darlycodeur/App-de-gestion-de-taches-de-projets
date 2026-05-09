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
import com.taskflow.app.database.entity.Tache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.ViewHolder> {

    private List<Tache> tasks = new ArrayList<>();
    private OnTaskClickListener taskClickListener;
    private OnTaskLongClickListener taskLongClickListener;

    public interface OnTaskClickListener {
        void onTaskClick(Tache task);
    }

    public interface OnTaskLongClickListener {
        void onTaskLongClick(Tache task);
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.taskClickListener = listener;
    }

    public void setOnTaskLongClickListener(OnTaskLongClickListener listener) {
        this.taskLongClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position), taskClickListener, taskLongClickListener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Tache> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Tache getTaskAt(int position) {
        return tasks.get(position);
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void addTask(int position, Tache task) {
        tasks.add(position, task);
        notifyItemInserted(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPriorityBadge;
        TextView tvTaskTitle;
        TextView tvTaskDescription;
        TextView tvAssignee;
        Chip chipStatus;
        TextView tvDueDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriorityBadge = itemView.findViewById(R.id.tv_priority_badge);
            tvTaskTitle = itemView.findViewById(R.id.tv_task_title);
            tvTaskDescription = itemView.findViewById(R.id.tv_task_description);
            tvAssignee = itemView.findViewById(R.id.tv_assignee);
            chipStatus = itemView.findViewById(R.id.chip_status);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
        }

        void bind(Tache task, OnTaskClickListener clickListener, OnTaskLongClickListener longClickListener) {
            tvTaskTitle.setText(task.getTitre());
            tvTaskDescription.setText(task.getDescription());
            tvPriorityBadge.setText(String.valueOf(task.getPriorite()));

            int priorityColor = getPriorityColor(task.getPriorite());
            GradientDrawable drawable = (GradientDrawable) tvPriorityBadge.getBackground();
            drawable.setColor(priorityColor);

            tvAssignee.setText(task.getAssigneAId() != null ? "Assigné" : "Non assigné");

            chipStatus.setText(task.getStatut());
            setStatusChipColor(chipStatus, task.getStatut());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            String dateStr = sdf.format(new Date(task.getDateEcheance()));
            tvDueDate.setText(dateStr);

            boolean isOverdue = task.getDateEcheance() < System.currentTimeMillis()
                    && !task.getStatut().equals("terminée");
            tvDueDate.setTextColor(isOverdue
                    ? Color.parseColor("#D32F2F")
                    : Color.parseColor("#757575"));

            itemView.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onTaskClick(task);
            });
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) longClickListener.onTaskLongClick(task);
                return true;
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
                default:
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
