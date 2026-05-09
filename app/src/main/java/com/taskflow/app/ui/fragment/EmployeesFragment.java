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
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.model.EmployeWithTacheCount;
import com.taskflow.app.ui.activity.EmployeeDetailActivity;
import com.taskflow.app.ui.adapter.EmployeesAdapter;
import com.taskflow.app.viewmodel.DashboardViewModel;

public class EmployeesFragment extends Fragment {

    private DashboardViewModel viewModel;
    private EmployeesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employees, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        RecyclerView rvEmployees = view.findViewById(R.id.rv_employees);
        rvEmployees.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EmployeesAdapter();
        rvEmployees.setAdapter(adapter);

        // Click → detail
        adapter.setOnEmployeeClickListener(employee -> {
            Intent intent = new Intent(getContext(), EmployeeDetailActivity.class);
            intent.putExtra(EmployeeDetailActivity.EXTRA_EMPLOYEE_ID, employee.employe.getId());
            startActivity(intent);
        });

        // Long click → edit / delete
        adapter.setOnEmployeeLongClickListener(employee ->
                showEmployeeOptionsDialog(employee.employe));

        viewModel.getEmployeeWorkload().observe(getViewLifecycleOwner(), employees -> {
            if (employees != null && !employees.isEmpty()) {
                adapter.setEmployees(employees);
                rvEmployees.setVisibility(View.VISIBLE);
                view.findViewById(R.id.empty_state).setVisibility(View.GONE);
            } else {
                rvEmployees.setVisibility(View.GONE);
                view.findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_employee);
        fab.setOnClickListener(v -> showAddEmployeeDialog(null));
    }

    private void showEmployeeOptionsDialog(Employe employe) {
        new AlertDialog.Builder(requireContext())
                .setTitle(employe.getNom() + " " + employe.getPrenom())
                .setItems(new String[]{"Modifier", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) showAddEmployeeDialog(employe);
                    else confirmDelete(employe);
                })
                .show();
    }

    private void confirmDelete(Employe employe) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Supprimer")
                .setMessage("Supprimer " + employe.getNom() + " " + employe.getPrenom() + " ?")
                .setPositiveButton("Supprimer", (d, w) -> {
                    viewModel.deleteEmployee(employe);
                    Toast.makeText(getContext(), "Employé supprimé", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showAddEmployeeDialog(@Nullable Employe existing) {
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_add_employee, null);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextInputEditText etNom = dialogView.findViewById(R.id.edittext_nom);
        TextInputEditText etPrenom = dialogView.findViewById(R.id.edittext_prenom);
        TextInputEditText etEmail = dialogView.findViewById(R.id.edittext_email);
        TextInputEditText etMatricule = dialogView.findViewById(R.id.edittext_matricule);
        MaterialButton btnCancel = dialogView.findViewById(R.id.btn_cancel);
        MaterialButton btnSave = dialogView.findViewById(R.id.btn_save);

        if (existing != null) {
            etNom.setText(existing.getNom());
            etPrenom.setText(existing.getPrenom());
            etEmail.setText(existing.getEmail());
            etMatricule.setText(existing.getNumMatricule());
            btnSave.setText("Modifier");
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnSave.setOnClickListener(v -> {
            String nom = etNom.getText() != null ? etNom.getText().toString().trim() : "";
            String prenom = etPrenom.getText() != null ? etPrenom.getText().toString().trim() : "";
            String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
            String matricule = etMatricule.getText() != null ? etMatricule.getText().toString().trim() : "";

            if (nom.isEmpty()) { etNom.setError("Requis"); return; }
            if (prenom.isEmpty()) { etPrenom.setError("Requis"); return; }
            if (email.isEmpty()) { etEmail.setError("Requis"); return; }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Format invalide"); return;
            }
            if (matricule.isEmpty()) { etMatricule.setError("Requis"); return; }

            if (existing != null) {
                existing.setNom(nom);
                existing.setPrenom(prenom);
                existing.setEmail(email);
                existing.setNumMatricule(matricule);
                viewModel.updateEmployee(existing);
                Toast.makeText(getContext(), "Employé modifié", Toast.LENGTH_SHORT).show();
            } else {
                Employe employe = new Employe();
                employe.setNom(nom);
                employe.setPrenom(prenom);
                employe.setEmail(email);
                employe.setNumMatricule(matricule);
                viewModel.insertEmployee(employe);
                Toast.makeText(getContext(), "Employé créé", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialog.show();
    }
}
