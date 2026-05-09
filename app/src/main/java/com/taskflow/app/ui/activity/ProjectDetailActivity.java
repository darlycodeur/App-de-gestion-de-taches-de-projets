package com.taskflow.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.taskflow.app.R;
import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.ui.adapter.TaskTabsAdapter;
import com.taskflow.app.viewmodel.ProjectDetailViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProjectDetailActivity extends AppCompatActivity {

    private ProjectDetailViewModel viewModel;
    private int projectId;
    private long selectedDueDate;
    private List<Employe> employees = new ArrayList<>();
    private List<Categorie> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        projectId = getIntent().getIntExtra("PROJECT_ID", 0);
        String projectName = getIntent().getStringExtra("PROJECT_NAME");

        viewModel = new ViewModelProvider(this).get(ProjectDetailViewModel.class);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(projectName);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Afficher les infos du projet
        TextView tvDescription = findViewById(R.id.tv_project_description);
        TextView tvDate = findViewById(R.id.tv_project_date);
        viewModel.getProjectById(projectId).observe(this, project -> {
            if (project != null) {
                toolbar.setTitle(project.getNom());
                tvDescription.setText(project.getDescription() != null ? project.getDescription() : "");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("Créé le dd/MM/yyyy", java.util.Locale.FRANCE);
                tvDate.setText(sdf.format(new java.util.Date(project.getDateCreation())));
            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        TaskTabsAdapter adapter = new TaskTabsAdapter(this, projectId);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Toutes les tâches");
            } else {
                tab.setText("Par catégorie");
            }
        }).attach();

        FloatingActionButton fab = findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> showAddTaskDialog());

        viewModel.getAllEmployees().observe(this, employeeList -> {
            if (employeeList != null) {
                employees = employeeList;
            }
        });

        viewModel.getCategoriesByProject(projectId).observe(this, categoryList -> {
            if (categoryList != null) {
                categories = categoryList;
            }
        });
    }

    private void showAddTaskDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task);

        TextInputEditText etTitle = dialog.findViewById(R.id.edittext_task_title);
        TextInputEditText etDescription = dialog.findViewById(R.id.edittext_task_description);
        TextInputEditText etDueDate = dialog.findViewById(R.id.edittext_due_date);
        Slider sliderPriority = dialog.findViewById(R.id.slider_priority);
        Spinner spinnerProject = dialog.findViewById(R.id.spinner_project);
        Spinner spinnerEmployee = dialog.findViewById(R.id.spinner_employee);
        Spinner spinnerCategory = dialog.findViewById(R.id.spinner_category);
        MaterialButton btnCancel = dialog.findViewById(R.id.btn_cancel);
        MaterialButton btnSave = dialog.findViewById(R.id.btn_save);

        // Le projet est déjà connu, on cache le spinner projet
        if (spinnerProject != null) spinnerProject.setVisibility(android.view.View.GONE);

        selectedDueDate = System.currentTimeMillis();

        etDueDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        selectedDueDate = calendar.getTimeInMillis();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                        etDueDate.setText(sdf.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        List<String> employeeNames = new ArrayList<>();
        employeeNames.add("Non assigné");
        for (Employe emp : employees) {
            employeeNames.add(emp.getNom() + " " + emp.getPrenom());
        }
        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employeeNames);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(employeeAdapter);

        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Sans catégorie");
        for (Categorie cat : categories) {
            categoryNames.add(cat.getNom());
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Le titre est requis", Toast.LENGTH_SHORT).show();
                return;
            }

            Tache tache = new Tache();
            tache.setTitre(title);
            tache.setDescription(description);
            tache.setDateEcheance(selectedDueDate);
            tache.setPriorite((int) sliderPriority.getValue());
            tache.setStatut("à faire");
            tache.setProjetId(projectId);

            int employeePosition = spinnerEmployee.getSelectedItemPosition();
            if (employeePosition > 0 && employees.size() >= employeePosition) {
                tache.setAssigneAId(employees.get(employeePosition - 1).getId());
            }

            int categoryPosition = spinnerCategory.getSelectedItemPosition();
            if (categoryPosition > 0 && categories.size() >= categoryPosition) {
                tache.setCategorieId(categories.get(categoryPosition - 1).getId());
            }

            viewModel.insertTask(tache);
            Toast.makeText(this, "Tâche créée", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
