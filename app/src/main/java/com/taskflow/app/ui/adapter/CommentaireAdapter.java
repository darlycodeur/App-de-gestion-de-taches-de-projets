package com.taskflow.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taskflow.app.R;
import com.taskflow.app.database.entity.Commentaire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.ViewHolder> {

    private List<Commentaire> commentaires = new ArrayList<>();
    private OnDeleteListener deleteListener;

    public interface OnDeleteListener {
        void onDelete(Commentaire commentaire);
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.deleteListener = listener;
    }

    public void setCommentaires(List<Commentaire> list) {
        this.commentaires = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_commentaire, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commentaire c = commentaires.get(position);
        holder.tvContenu.setText(c.getContenu());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        holder.tvDate.setText(sdf.format(new Date(c.getDateCreation())));
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onDelete(c);
        });
    }

    @Override
    public int getItemCount() {
        return commentaires.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContenu, tvDate;
        ImageButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContenu = itemView.findViewById(R.id.tv_commentaire_contenu);
            tvDate = itemView.findViewById(R.id.tv_commentaire_date);
            btnDelete = itemView.findViewById(R.id.btn_delete_commentaire);
        }
    }
}
