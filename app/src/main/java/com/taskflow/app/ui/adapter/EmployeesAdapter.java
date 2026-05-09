package com.taskflow.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.imageview.ShapeableImageView;
import com.taskflow.app.R;
import com.taskflow.app.database.model.EmployeWithTacheCount;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {

    private List<EmployeWithTacheCount> employees = new ArrayList<>();
    private OnEmployeeClickListener listener;
    private OnEmployeeLongClickListener longClickListener;

    public interface OnEmployeeClickListener {
        void onEmployeeClick(EmployeWithTacheCount employee);
    }

    public interface OnEmployeeLongClickListener {
        void onEmployeeLongClick(EmployeWithTacheCount employee);
    }

    public void setOnEmployeeClickListener(OnEmployeeClickListener listener) {
        this.listener = listener;
    }

    public void setOnEmployeeLongClickListener(OnEmployeeLongClickListener listener) {
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(employees.get(position), listener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setEmployees(List<EmployeWithTacheCount> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivAvatar;
        TextView tvFullName;
        TextView tvMatricule;
        Chip chipTaskCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
            tvMatricule = itemView.findViewById(R.id.tv_matricule);
            chipTaskCount = itemView.findViewById(R.id.chip_task_count);
        }

        void bind(EmployeWithTacheCount employee, OnEmployeeClickListener listener,
                OnEmployeeLongClickListener longClickListener) {
            tvFullName.setText(employee.employe.getNom() + " " + employee.employe.getPrenom());
            tvMatricule.setText(employee.employe.getNumMatricule());
            chipTaskCount.setText(employee.tacheCount + " tâche" + (employee.tacheCount > 1 ? "s" : ""));

            if (employee.employe.getAvatar() != null && !employee.employe.getAvatar().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(employee.employe.getAvatar())
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.ic_person)
                        .error(R.drawable.ic_person)
                        .into(ivAvatar);
            } else {
                ivAvatar.setImageResource(R.drawable.ic_person);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onEmployeeClick(employee);
            });
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) longClickListener.onEmployeeLongClick(employee);
                return true;
            });
        }
    }
}
