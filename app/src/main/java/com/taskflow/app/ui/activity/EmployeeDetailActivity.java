package com.taskflow.app.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.taskflow.app.R;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.ui.adapter.TacheDetailAdapter;
import com.taskflow.app.viewmodel.EmployeeDetailViewModel;

public class EmployeeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EMPLOYEE_ID = "EMPLOYEE_ID";

    private EmployeeDetailViewModel viewModel;
    private int employeeId;
    private Employe currentEmployee;
    private ShapeableImageView ivAvatar;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) saveAvatarUri(uri);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        employeeId = getIntent().getIntExtra(EXTRA_EMPLOYEE_ID, 0);
        viewModel = new ViewModelProvider(this).get(EmployeeDetailViewModel.class);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        ivAvatar = findViewById(R.id.iv_avatar);
        TextView tvNom = findViewById(R.id.tv_nom);
        TextView tvPrenom = findViewById(R.id.tv_prenom);
        TextView tvEmail = findViewById(R.id.tv_email);
        TextView tvMatricule = findViewById(R.id.tv_matricule);

        RecyclerView rvTasks = findViewById(R.id.rv_employee_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        TacheDetailAdapter taskAdapter = new TacheDetailAdapter();
        rvTasks.setAdapter(taskAdapter);

        viewModel.getEmployeeById(employeeId).observe(this, employe -> {
            if (employe == null) return;
            currentEmployee = employe;
            toolbar.setTitle(employe.getNom() + " " + employe.getPrenom());
            tvNom.setText(employe.getNom());
            tvPrenom.setText(employe.getPrenom());
            tvEmail.setText(employe.getEmail());
            tvMatricule.setText(employe.getNumMatricule());

            if (employe.getAvatar() != null && !employe.getAvatar().isEmpty()) {
                Glide.with(this)
                        .load(employe.getAvatar())
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.ic_person)
                        .into(ivAvatar);
            } else {
                ivAvatar.setImageResource(R.drawable.ic_person);
            }
        });

        viewModel.getTasksByEmployee(employeeId).observe(this, tasks -> {
            if (tasks != null) taskAdapter.setTaches(tasks);
        });

        findViewById(R.id.btn_change_avatar).setOnClickListener(v ->
                imagePickerLauncher.launch("image/*"));
    }

    private void saveAvatarUri(Uri uri) {
        try {
            getContentResolver().takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (Exception ignored) {}

        if (currentEmployee != null) {
            currentEmployee.setAvatar(uri.toString());
            viewModel.updateEmployee(currentEmployee);
            Glide.with(this)
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivAvatar);
            Toast.makeText(this, "Photo mise à jour", Toast.LENGTH_SHORT).show();
        }
    }
}
