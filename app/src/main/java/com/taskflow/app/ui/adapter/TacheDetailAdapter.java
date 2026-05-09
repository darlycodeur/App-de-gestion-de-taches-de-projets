package com.taskflow.app.ui.adapter;

import android.content.Intent;
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
import com.taskflow.app.database.model.TacheDetail;
import com.taskflow.app.ui.activity.TaskDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TacheDetailAdapter extends RecyclerView.Adapter<TacheDetailAdapter.ViewHolder> {

    private List<TacheDetail> taches = new ArrayList<>();

    public void setTaches(List<TacheDetail> list) {
        this.taches = list;
        notifyDataSetChanged();
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
        TacheDetail detail = taches.get(position);
        holder.tvTitle.setText(detail.tache.getTitre());
        holder.tvDescription.setText(detail.tache.getDescription());

        holder.tvPriorityBadge.setText(String.valueOf(detail.tache.getPriorite()));
        GradientDrawable drawable = (GradientDrawable) holder.tvPriorityBadge.getBackground();
        drawable.setColor(getPriorityColor(detail.tache.getPriorite()));

        holder.chipStatus.setText(detail.tache.getStatut());
        setStatusChipColor(holder.chipStatus, detail.tache.getStatut());

        // Afficher le nom du projet au lieu de l'assigné (contexte employé)
        holder.tvAssignee.setText(detail.projetNom != null ? detail.projetNom : "—");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        holder.tvDueDate.setText(sdf.format(new Date(detail.tache.getDateEcheance())));

        boolean isOverdue = detail.tache.getDateEcheance() < System.currentTimeMillis()
                && !detail.tache.getStatut().equals("terminée");
        holder.tvDueDate.setTextColor(isOverdue
                ? Color.parseColor("#D32F2F") : Color.parseColor("#757575"));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TaskDetailActivity.class);
            intent.putExtra("TASK_ID", detail.tache.getId());
            intent.putExtra("TASK_TITLE", detail.tache.getTitre());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taches.size();
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPriorityBadge, tvTitle, tvDescription, tvAssignee, tvDueDate;
        Chip chipStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPriorityBadge = itemView.findViewById(R.id.tv_priority_badge);
            tvTitle = itemView.findViewById(R.id.tv_task_title);
            tvDescription = itemView.findViewById(R.id.tv_task_description);
            tvAssignee = itemView.findViewById(R.id.tv_assignee);
            chipStatus = itemView.findViewById(R.id.chip_status);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
        }
    }
}
