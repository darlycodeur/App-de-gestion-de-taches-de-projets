package com.taskflow.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taskflow.app.database.AppDatabase;
import com.taskflow.app.database.dao.EmployeDao;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.model.EmployeWithTacheCount;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeRepository {
    private EmployeDao employeDao;
    private LiveData<List<Employe>> allEmployes;
    private ExecutorService executorService;

    public EmployeRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        employeDao = database.employeDao();
        allEmployes = employeDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Employe employe) {
        executorService.execute(() -> employeDao.insert(employe));
    }

    public void update(Employe employe) {
        executorService.execute(() -> employeDao.update(employe));
    }

    public void delete(Employe employe) {
        executorService.execute(() -> employeDao.delete(employe));
    }

    public void deleteAll() {
        executorService.execute(() -> employeDao.deleteAll());
    }

    public LiveData<Employe> getById(int id) {
        return employeDao.getById(id);
    }

    public LiveData<List<Employe>> getAllEmployes() {
        return allEmployes;
    }

    public LiveData<Employe> getByEmail(String email) {
        return employeDao.getByEmail(email);
    }

    public LiveData<Employe> getByNumMatricule(String numMatricule) {
        return employeDao.getByNumMatricule(numMatricule);
    }

    public LiveData<List<EmployeWithTacheCount>> getEmployeWithTacheCount() {
        return employeDao.getEmployeWithTacheCount();
    }
}
