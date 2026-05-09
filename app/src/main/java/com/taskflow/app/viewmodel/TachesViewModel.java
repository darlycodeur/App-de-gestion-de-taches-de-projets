package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.repository.TacheRepository;

import java.util.List;

public class TachesViewModel extends AndroidViewModel {

    private TacheRepository repository;

    public TachesViewModel(@NonNull Application application) {
        super(application);
        repository = new TacheRepository(application);
    }

    public LiveData<List<Tache>> getAllTasks() {
        return repository.getAllTaches();
    }

    public LiveData<List<Tache>> getTasksByStatus(String status) {
        return repository.getTachesByStatut(status);
    }

    public LiveData<List<Tache>> getOverdueTasks() {
        return repository.getTachesEnRetard(System.currentTimeMillis());
    }

    public void insertTask(Tache task) {
        repository.insert(task);
    }

    public void deleteTask(Tache task) {
        repository.delete(task);
    }

    public void updateTask(Tache task) {
        repository.update(task);
    }
}