package com.taskflow.app.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.taskflow.app.R;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.model.ProjectWithTacheCount;
import com.taskflow.app.ui.activity.ProjectDetailActivity;
import com.taskflow.app.ui.adapter.ProjectsAdapter;
import com.taskflow.app.viewmodel.ProjectsViewModel;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel viewModel;
    private ProjectsAdapter adapter;
    private View emptyState;
    private RecyclerView rvProjects;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        viewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);

        rvProjects = view.findViewById(R.id.rv_projects);
        emptyState = view.findViewById(R.id.empty_state_projects);
        rvProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProjectsAdapter();
        rvProjects.setAdapter(adapter);

        // Click → detail
        adapter.setOnProjectClickListener(project -> {
            Intent intent = new Intent(getContext(), ProjectDetailActivity.class);
            intent.putExtra("PROJECT_ID", project.project.getId());
            intent.putExtra("PROJECT_NAME", project.project.getNom());
            startActivity(intent);
        });

        // Long click → edit / delete
        adapter.setOnProjectLongClickListener(project ->
                showProjectOptionsDialog(project));

        viewModel.getAllProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                adapter.setProjects(projects);
                boolean isEmpty = projects.isEmpty();
                rvProjects.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                emptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_project);
        fab.setOnClickListener(v -> showAddProjectDialog(null));

        return view;
    }

    private void showProjectOptionsDialog(ProjectWithTacheCount pwt) {
        new AlertDialog.Builder(requireContext())
                .setTitle(pwt.project.getNom())
                .setItems(new String[]{"Modifier", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) showAddProjectDialog(pwt.project);
                    else confirmDelete(pwt.project);
                })
                .show();
    }

    private void confirmDelete(Project project) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Supprimer")
                .setMessage("Supprimer le projet \"" + project.getNom() + "\" et toutes ses tâches ?")
                .setPositiveButton("Supprimer", (d, w) -> {
                    viewModel.delete(project);
                    Toast.makeText(getContext(), "Projet supprimé", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showAddProjectDialog(@Nullable Project existing) {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_project, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText etName = dialogView.findViewById(R.id.edittext_project_name);
        TextInputEditText etDescription = dialogView.findViewById(R.id.edittext_project_description);
        MaterialButton btnCancel = dialogView.findViewById(R.id.btn_cancel);
        MaterialButton btnSave = dialogView.findViewById(R.id.btn_save);

        if (existing != null) {
            etName.setText(existing.getNom());
            etDescription.setText(existing.getDescription());
            btnSave.setText("Modifier");
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String name = etName.getText() != null ? etName.getText().toString().trim() : "";
            String desc = etDescription.getText() != null ? etDescription.getText().toString().trim() : "";

            if (name.isEmpty()) { etName.setError("Requis"); return; }

            if (existing != null) {
                existing.setNom(name);
                existing.setDescription(desc);
                viewModel.update(existing);
                Toast.makeText(getContext(), "Projet modifié", Toast.LENGTH_SHORT).show();
            } else {
                Project project = new Project();
                project.setNom(name);
                project.setDescription(desc);
                project.setDateCreation(System.currentTimeMillis());
                viewModel.insert(project);
                Toast.makeText(getContext(), "Projet créé", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialog.show();
    }
}
