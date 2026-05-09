package com.taskflow.app.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.taskflow.app.R;
import com.taskflow.app.database.entity.Commentaire;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.TacheDetail;
import com.taskflow.app.ui.adapter.CommentaireAdapter;
import com.taskflow.app.viewmodel.TaskDetailViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity {

    private TaskDetailViewModel viewModel;
    private int taskId;
    private TacheDetail currentDetail;

    private TextView tvPriorityBadge, tvTaskTitle, tvTaskDescription;
    private TextView tvAssignee, tvDueDate, tvProjet, tvCategorie, tvNoCommentaires;
    private Chip chipStatus;
    private CommentaireAdapter commentaireAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskId = getIntent().getIntExtra("TASK_ID", 0);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvPriorityBadge = findViewById(R.id.tv_priority_badge);
        tvTaskTitle = findViewById(R.id.tv_task_title);
        tvTaskDescription = findViewById(R.id.tv_task_description);
        chipStatus = findViewById(R.id.chip_status);
        tvAssignee = findViewById(R.id.tv_assignee);
        tvDueDate = findViewById(R.id.tv_due_date);
        tvProjet = findViewById(R.id.tv_projet);
        tvCategorie = findViewById(R.id.tv_categorie);
        tvNoCommentaires = findViewById(R.id.tv_no_commentaires);

        RecyclerView rvCommentaires = findViewById(R.id.rv_commentaires);
        rvCommentaires.setLayoutManager(new LinearLayoutManager(this));
        commentaireAdapter = new CommentaireAdapter();
        rvCommentaires.setAdapter(commentaireAdapter);

        commentaireAdapter.setOnDeleteListener(c -> {
            new AlertDialog.Builder(this)
                    .setTitle("Supprimer ce commentaire ?")
                    .setPositiveButton("Supprimer", (d, w) -> viewModel.deleteCommentaire(c))
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        viewModel = new ViewModelProvider(this).get(TaskDetailViewModel.class);

        viewModel.getTacheDetail(taskId).observe(this, detail -> {
            if (detail != null) {
                currentDetail = detail;
                toolbar.setTitle(detail.tache.getTitre());
                bindDetail(detail);
            }
        });

        viewModel.getCommentaires(taskId).observe(this, commentaires -> {
            if (commentaires != null && !commentaires.isEmpty()) {
                commentaireAdapter.setCommentaires(commentaires);
                tvNoCommentaires.setVisibility(View.GONE);
                rvCommentaires.setVisibility(View.VISIBLE);
            } else {
                tvNoCommentaires.setVisibility(View.VISIBLE);
                rvCommentaires.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_add_commentaire).setOnClickListener(v -> showAddCommentaireDialog());
        findViewById(R.id.btn_change_status).setOnClickListener(v -> showChangeStatusDialog());
    }

    private void bindDetail(TacheDetail detail) {
        Tache task = detail.tache;

        tvTaskTitle.setText(task.getTitre());
        tvTaskDescription.setText(task.getDescription() != null && !task.getDescription().isEmpty()
                ? task.getDescription() : "Aucune description");

        tvPriorityBadge.setText(String.valueOf(task.getPriorite()));
        GradientDrawable drawable = (GradientDrawable) tvPriorityBadge.getBackground();
        drawable.setColor(getPriorityColor(task.getPriorite()));

        chipStatus.setText(task.getStatut());
        setStatusChipColor(chipStatus, task.getStatut());

        tvProjet.setText(detail.projetNom != null ? detail.projetNom : "—");
        tvCategorie.setText(detail.categorieNom != null ? detail.categorieNom : "—");
        tvAssignee.setText(detail.getEmployeFullName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        tvDueDate.setText(sdf.format(new Date(task.getDateEcheance())));

        boolean isOverdue = task.getDateEcheance() < System.currentTimeMillis()
                && !task.getStatut().equals("terminée");
        tvDueDate.setTextColor(isOverdue
                ? Color.parseColor("#D32F2F")
                : Color.parseColor("#757575"));
    }

    private void showAddCommentaireDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_commentaire, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText etContenu = dialogView.findViewById(R.id.edittext_commentaire);
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btn_save).setOnClickListener(v -> {
            String contenu = etContenu.getText() != null ? etContenu.getText().toString().trim() : "";
            if (contenu.isEmpty()) { etContenu.setError("Requis"); return; }

            Commentaire commentaire = new Commentaire();
            commentaire.setContenu(contenu);
            commentaire.setDateCreation(System.currentTimeMillis());
            commentaire.setTacheId(taskId);
            viewModel.insertCommentaire(commentaire);
            Toast.makeText(this, "Commentaire ajouté", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showChangeStatusDialog() {
        if (currentDetail == null) return;
        String[] statuts = {"à faire", "en cours", "terminée"};
        new AlertDialog.Builder(this)
                .setTitle("Changer le statut")
                .setItems(statuts, (d, which) -> {
                    currentDetail.tache.setStatut(statuts[which]);
                    // Update via repository directly through ViewModel
                    new androidx.lifecycle.ViewModelProvider(this)
                            .get(com.taskflow.app.viewmodel.TachesViewModel.class)
                            .updateTask(currentDetail.tache);
                    chipStatus.setText(statuts[which]);
                    setStatusChipColor(chipStatus, statuts[which]);
                    Toast.makeText(this, "Statut mis à jour", Toast.LENGTH_SHORT).show();
                })
                .show();
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
