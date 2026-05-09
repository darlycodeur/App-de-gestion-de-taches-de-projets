package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.repository.CategorieRepository;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {

    private final CategorieRepository repository;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        repository = new CategorieRepository(application);
    }

    public LiveData<List<Categorie>> getAllCategories() {
        return repository.getAllCategories();
    }

    public void insert(Categorie categorie) {
        repository.insert(categorie);
    }

    public void delete(Categorie categorie) {
        repository.delete(categorie);
    }
}
