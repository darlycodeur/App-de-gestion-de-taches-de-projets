package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.repository.CategorieRepository;
import com.taskflow.app.repository.EmployeRepository;
import com.taskflow.app.repository.ProjectRepository;
import com.taskflow.app.repository.TacheRepository;

import java.util.List;

public class ProjectDetailViewModel extends AndroidViewModel {

    private TacheRepository tacheRepository;
    private EmployeRepository employeRepository;
    private CategorieRepository categorieRepository;
    private ProjectRepository projectRepository;

    public ProjectDetailViewModel(@NonNull Application application) {
        super(application);
        tacheRepository = new TacheRepository(application);
        employeRepository = new EmployeRepository(application);
        categorieRepository = new CategorieRepository(application);
        projectRepository = new ProjectRepository(application);
    }

    public LiveData<Project> getProjectById(int id) {
        return projectRepository.getById(id);
    }

    public LiveData<List<Tache>> getTasksByProject(int projectId) {
        return tacheRepository.getTachesByProjet(projectId);
    }

    public LiveData<List<Employe>> getAllEmployees() {
        return employeRepository.getAllEmployes();
    }

    public LiveData<List<Categorie>> getCategoriesByProject(int projectId) {
        return categorieRepository.getByProjetId(projectId);
    }

    public void insertTask(Tache tache) {
        tacheRepository.insert(tache);
    }

    public void updateTask(Tache tache) {
        tacheRepository.update(tache);
    }

    public void deleteTask(Tache tache) {
        tacheRepository.delete(tache);
    }
}
