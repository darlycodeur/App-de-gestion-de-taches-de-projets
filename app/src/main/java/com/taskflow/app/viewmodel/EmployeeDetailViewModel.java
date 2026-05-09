package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.TacheDetail;
import com.taskflow.app.repository.EmployeRepository;
import com.taskflow.app.repository.TacheRepository;

import java.util.List;

public class EmployeeDetailViewModel extends AndroidViewModel {

    private final EmployeRepository employeRepository;
    private final TacheRepository tacheRepository;

    public EmployeeDetailViewModel(@NonNull Application application) {
        super(application);
        employeRepository = new EmployeRepository(application);
        tacheRepository = new TacheRepository(application);
    }

    public LiveData<Employe> getEmployeeById(int id) {
        return employeRepository.getById(id);
    }

    public LiveData<List<TacheDetail>> getTasksByEmployee(int employeeId) {
        return tacheRepository.getTacheDetailsByEmploye(employeeId);
    }

    public void updateEmployee(Employe employe) {
        employeRepository.update(employe);
    }

    public void deleteEmployee(Employe employe) {
        employeRepository.delete(employe);
    }
}
