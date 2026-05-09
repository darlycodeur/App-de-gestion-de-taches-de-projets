package com.taskflow.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taskflow.app.database.AppDatabase;
import com.taskflow.app.database.dao.CategorieDao;
import com.taskflow.app.database.entity.Categorie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategorieRepository {
    private CategorieDao categorieDao;
    private LiveData<List<Categorie>> allCategories;
    private ExecutorService executorService;

    public CategorieRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        categorieDao = database.categorieDao();
        allCategories = categorieDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Categorie categorie) {
        executorService.execute(() -> categorieDao.insert(categorie));
    }

    public void update(Categorie categorie) {
        executorService.execute(() -> categorieDao.update(categorie));
    }

    public void delete(Categorie categorie) {
        executorService.execute(() -> categorieDao.delete(categorie));
    }

    public void deleteAll() {
        executorService.execute(() -> categorieDao.deleteAll());
    }

    public LiveData<Categorie> getById(int id) {
        return categorieDao.getById(id);
    }

    public LiveData<List<Categorie>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Categorie>> getByProjetId(int projetId) {
        return categorieDao.getByProjetId(projetId);
    }

    public List<Categorie> getByProjetIdSync(int projetId) {
        return categorieDao.getByProjetIdSync(projetId);
    }
}
