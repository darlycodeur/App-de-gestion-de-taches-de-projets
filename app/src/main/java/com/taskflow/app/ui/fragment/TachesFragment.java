package com.taskflow.app.ui.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import com.taskflow.app.R;
import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.repository.CategorieRepository;
import com.taskflow.app.ui.activity.TaskDetailActivity;
import com.taskflow.app.ui.adapter.TaskDetailAdapter;
import com.taskflow.app.viewmodel.DashboardViewModel;
import com.taskflow.app.viewmodel.ProjectsViewModel;
import com.taskflow.app.viewmodel.TachesViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TachesFragment extends Fragment {

    private TachesViewModel viewModel;
    private ProjectsViewModel projectsViewModel;
    private DashboardViewModel dashboardViewModel;
    private TaskDetailAdapter adapter;
    private String currentFilter = "all";
    private View emptyState;
    private RecyclerView rvTasks;
    private List<Project> projectList = new ArrayList<>();
    private List<Employe> employeeList = new ArrayList<>();
    private long selectedDueDate = System.currentTimeMillis();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taches, container, false);

        viewModel = new ViewModelProvider(this).get(TachesViewModel.class);
        projectsViewModel = new ViewModelProvider(this).get(ProjectsViewModel.class);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        rvTasks = view.findViewById(R.id.rv_tasks);
        emptyState = view.findViewById(R.id.empty_state_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskDetailAdapter();
        rvTasks.setAdapter(adapter);

        adapter.setOnTaskClickListener(task -> {
            Intent intent = new Intent(getContext(), TaskDetailActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            intent.putExtra("TASK_TITLE", task.getTitre());
            startActivity(intent);
        });

        adapter.setOnTaskLongClickListener(this::showTaskOptionsDialog);

        projectsViewModel.getAllProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                projectList.clear();
                for (com.taskflow.app.database.model.ProjectWithTacheCount p : projects) {
                    projectList.add(p.project);
                }
            }
        });

        dashboardViewModel.getEmployeeWorkload().observe(getViewLifecycleOwner(), employees -> {
            if (employees != null) {
                employeeList.clear();
                for (com.taskflow.app.database.model.EmployeWithTacheCount e : employees) {
                    employeeList.add(e.employe);
                }
            }
        });

        setupSwipeToDelete(rvTasks);
        setupFilterChips(view);
        loadTasks();

        FloatingActionButton fab = view.findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_task, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText etTitle = dialogView.findViewById(R.id.edittext_task_title);
        TextInputEditText etDescription = dialogView.findViewById(R.id.edittext_task_description);
        TextInputEditText etDueDate = dialogView.findViewById(R.id.edittext_due_date);
        Slider sliderPriority = dialogView.findViewById(R.id.slider_priority);
        Spinner spinnerProject = dialogView.findViewById(R.id.spinner_project);
        Spinner spinnerEmployee = dialogView.findViewById(R.id.spinner_employee);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinner_category);

        selectedDueDate = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        etDueDate.setText(sdf.format(selectedDueDate));

        etDueDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (dp, y, m, d) -> {
                cal.set(y, m, d);
                selectedDueDate = cal.getTimeInMillis();
                etDueDate.setText(sdf.format(cal.getTime()));
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Spinner Projet
        List<String> projectNames = new ArrayList<>();
        projectNames.add("Sélectionner un projet *");
        for (Project p : projectList) projectNames.add(p.getNom());
        ArrayAdapter<String> projAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, projectNames);
        projAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProject.setAdapter(projAdapter);

        // Spinner Employé
        List<String> empNames = new ArrayList<>();
        empNames.add("Non assigné");
        for (Employe e : employeeList) empNames.add(e.getNom() + " " + e.getPrenom());
        ArrayAdapter<String> empAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, empNames);
        empAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmployee.setAdapter(empAdapter);

        // Spinner Catégorie — se met à jour selon le projet sélectionné
        final List<Categorie> categoriesForProject = new ArrayList<>();
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        CategorieRepository categorieRepository = new CategorieRepository(requireActivity().getApplication());

        spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriesForProject.clear();
                catAdapter.clear();
                catAdapter.add("Sans catégorie");
                if (position > 0) {
                    int projetId = projectList.get(position - 1).getId();
                    new Thread(() -> {
                        List<Categorie> cats = categorieRepository.getByProjetIdSync(projetId);
                        requireActivity().runOnUiThread(() -> {
                            categoriesForProject.addAll(cats);
                            for (Categorie c : cats) catAdapter.add(c.getNom());
                            catAdapter.notifyDataSetChanged();
                        });
                    }).start();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btn_save).setOnClickListener(v -> {
            String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            if (title.isEmpty()) { etTitle.setError("Requis"); return; }

            int projPos = spinnerProject.getSelectedItemPosition();
            if (projPos == 0) {
                Toast.makeText(getContext(), "Sélectionnez un projet", Toast.LENGTH_SHORT).show();
                return;
            }

            Tache tache = new Tache();
            tache.setTitre(title);
            tache.setDescription(etDescription.getText() != null
                    ? etDescription.getText().toString().trim() : "");
            tache.setDateEcheance(selectedDueDate);
            tache.setPriorite((int) sliderPriority.getValue());
            tache.setStatut("à faire");
            tache.setProjetId(projectList.get(projPos - 1).getId());

            int empPos = spinnerEmployee.getSelectedItemPosition();
            if (empPos > 0 && empPos <= employeeList.size()) {
                tache.setAssigneAId(employeeList.get(empPos - 1).getId());
            }

            int catPos = spinnerCategory.getSelectedItemPosition();
            if (catPos > 0 && catPos <= categoriesForProject.size()) {
                tache.setCategorieId(categoriesForProject.get(catPos - 1).getId());
            }

            viewModel.insertTask(tache);
            Toast.makeText(getContext(), "Tâche créée", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showTaskOptionsDialog(Tache task) {
        new AlertDialog.Builder(requireContext())
                .setTitle(task.getTitre())
                .setItems(new String[]{"Marquer terminée", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) {
                        task.setStatut("terminée");
                        viewModel.updateTask(task);
                    } else {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Supprimer")
                                .setMessage("Supprimer \"" + task.getTitre() + "\" ?")
                                .setPositiveButton("Supprimer", (d, w) -> viewModel.deleteTask(task))
                                .setNegativeButton("Annuler", null)
                                .show();
                    }
                })
                .show();
    }

    private void setupFilterChips(View view) {
        ChipGroup chipGroup = view.findViewById(R.id.chip_group_filters);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int checkedId = checkedIds.get(0);
            if (checkedId == R.id.chip_all) currentFilter = "all";
            else if (checkedId == R.id.chip_todo) currentFilter = "à faire";
            else if (checkedId == R.id.chip_inprogress) currentFilter = "en cours";
            else if (checkedId == R.id.chip_completed) currentFilter = "terminée";
            else if (checkedId == R.id.chip_overdue) currentFilter = "overdue";
            loadTasks();
        });
    }

    private void loadTasks() {
        if (currentFilter.equals("all")) {
            viewModel.getAllTasks().observe(getViewLifecycleOwner(), this::updateList);
        } else if (currentFilter.equals("overdue")) {
            viewModel.getOverdueTasks().observe(getViewLifecycleOwner(), this::updateList);
        } else {
            viewModel.getTasksByStatus(currentFilter).observe(getViewLifecycleOwner(), this::updateList);
        }
    }

    private void updateList(List<Tache> tasks) {
        if (tasks != null) {
            adapter.setTasks(tasks);
            boolean isEmpty = tasks.isEmpty();
            rvTasks.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            emptyState.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        }
    }

    private void setupSwipeToDelete(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh,
                    @NonNull RecyclerView.ViewHolder target) { return false; }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Tache task = adapter.getTaskAt(position);
                // Restore item immediately so it doesn't disappear before confirmation
                adapter.notifyItemChanged(position);

                if (direction == ItemTouchHelper.LEFT) {
                    // Swipe gauche = supprimer avec confirmation
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Supprimer la tâche")
                            .setMessage("Voulez-vous vraiment supprimer \"" + task.getTitre() + "\" ? Cette action est irréversible.")
                            .setPositiveButton("Supprimer", (d, w) -> viewModel.deleteTask(task))
                            .setNegativeButton("Annuler", null)
                            .show();
                } else {
                    // Swipe droit = modifier le statut
                    String[] statuts = {"\u00e0 faire", "en cours", "terminée"};
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Changer le statut de \"" + task.getTitre() + "\"")
                            .setItems(statuts, (d, which) -> {
                                task.setStatut(statuts[which]);
                                viewModel.updateTask(task);
                            })
                            .setNegativeButton("Annuler", null)
                            .show();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }
}
