package com.taskflow.app.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taskflow.app.R;
import com.taskflow.app.database.entity.Categorie;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Categorie> categories = new ArrayList<>();
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Categorie categorie);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.get(position), deleteListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View viewColor;
        TextView tvName;
        ImageView btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewColor = itemView.findViewById(R.id.view_color);
            tvName = itemView.findViewById(R.id.tv_category_name);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        void bind(Categorie categorie, OnDeleteClickListener deleteListener) {
            tvName.setText(categorie.getNom());
            try {
                GradientDrawable drawable = (GradientDrawable) viewColor.getBackground();
                drawable.setColor(Color.parseColor(categorie.getCouleur()));
            } catch (Exception ignored) {}

            btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDeleteClick(categorie);
            });
        }
    }
}
