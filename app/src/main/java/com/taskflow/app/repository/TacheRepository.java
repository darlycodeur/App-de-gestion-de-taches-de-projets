package com.taskflow.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taskflow.app.database.AppDatabase;
import com.taskflow.app.database.dao.TacheDao;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.TacheDetail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TacheRepository {
    private TacheDao tacheDao;
    private LiveData<List<Tache>> allTaches;
    private ExecutorService executorService;

    public TacheRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        tacheDao = database.tacheDao();
        allTaches = tacheDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Tache tache) {
        executorService.execute(() -> tacheDao.insert(tache));
    }

    public void update(Tache tache) {
        executorService.execute(() -> tacheDao.update(tache));
    }

    public void delete(Tache tache) {
        executorService.execute(() -> tacheDao.delete(tache));
    }

    public void deleteAll() {
        executorService.execute(() -> tacheDao.deleteAll());
    }

    public LiveData<Tache> getById(int id) {
        return tacheDao.getById(id);
    }

    public LiveData<List<Tache>> getAllTaches() {
        return allTaches;
    }

    public LiveData<List<Tache>> getTachesByProjet(int projetId) {
        return tacheDao.getTachesByProjet(projetId);
    }

    public LiveData<List<Tache>> getTachesByEmploye(int employeId) {
        return tacheDao.getTachesByEmploye(employeId);
    }

    public LiveData<List<Tache>> getTachesByStatut(String statut) {
        return tacheDao.getTachesByStatut(statut);
    }

    public LiveData<List<Tache>> getTachesEnRetard(long currentTime) {
        return tacheDao.getTachesEnRetard(currentTime);
    }

    public LiveData<Integer> countByStatut(String statut) {
        return tacheDao.countByStatut(statut);
    }

    public LiveData<Integer> countByProjet(int projetId) {
        return tacheDao.countByProjet(projetId);
    }

    public LiveData<TacheDetail> getTacheDetailById(int id) {
        return tacheDao.getTacheDetailById(id);
    }

    public LiveData<List<TacheDetail>> getTacheDetailsByEmploye(int employeId) {
        return tacheDao.getTacheDetailsByEmploye(employeId);
    }
}
