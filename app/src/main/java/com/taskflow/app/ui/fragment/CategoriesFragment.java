package com.taskflow.app.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.ui.adapter.CategoriesAdapter;
import com.taskflow.app.viewmodel.CategoriesViewModel;
import com.taskflow.app.viewmodel.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private CategoriesViewModel viewModel;
    private ProjectsViewModel projectsViewModel;
    private CategoriesAdapter adapter;
    private List<Project> projectList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);

        RecyclerView rvCategories = view.findViewById(R.id.rv_categories);
        View emptyState = view.findViewById(R.id.empty_state_categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoriesAdapter();
        rvCategories.setAdapter(adapter);

        adapter.setOnDeleteClickListener(categorie -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Supprimer")
                    .setMessage("Supprimer la catégorie \"" + categorie.getNom() + "\" ?")
                    .setPositiveButton("Supprimer", (d, w) -> {
                        viewModel.delete(categorie);
                        Toast.makeText(getContext(), "Catégorie supprimée", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        projectsViewModel.getAllProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                projectList.clear();
                for (com.taskflow.app.database.model.ProjectWithTacheCount p : projects) {
                    projectList.add(p.project);
                }
            }
        });

        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                adapter.setCategories(categories);
                boolean isEmpty = categories.isEmpty();
                rvCategories.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
                emptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_category);
        fab.setOnClickListener(v -> showAddCategoryDialog());

        return view;
    }

    private void showAddCategoryDialog() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_category, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText etName = dialogView.findViewById(R.id.edittext_category_name);
        TextInputEditText etColor = dialogView.findViewById(R.id.edittext_category_color);
        Spinner spinnerProject = dialogView.findViewById(R.id.spinner_project_category);
        MaterialButton btnCancel = dialogView.findViewById(R.id.btn_cancel);
        MaterialButton btnSave = dialogView.findViewById(R.id.btn_save);

        List<String> projectNames = new ArrayList<>();
        projectNames.add("Sélectionner un projet *");
        for (Project p : projectList) projectNames.add(p.getNom());
        ArrayAdapter<String> projAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, projectNames);
        projAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(projAdapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String name = etName.getText() != null ? etName.getText().toString().trim() : "";
            String color = etColor.getText() != null ? etColor.getText().toString().trim() : "#1976D2";

            if (name.isEmpty()) { etName.setError("Requis"); return; }

            int projPos = spinnerProject.getSelectedItemPosition();
            if (projPos == 0) {
                Toast.makeText(getContext(), "Sélectionnez un projet", Toast.LENGTH_SHORT).show();
                return;
            }

            Categorie categorie = new Categorie();
            categorie.setNom(name);
            categorie.setCouleur(color.startsWith("#") ? color : "#" + color);
            categorie.setProjetId(projectList.get(projPos - 1).getId());

            viewModel.insert(categorie);
            Toast.makeText(getContext(), "Catégorie créée", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
