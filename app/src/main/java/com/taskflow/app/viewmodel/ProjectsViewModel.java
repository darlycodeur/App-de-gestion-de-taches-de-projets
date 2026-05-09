package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.model.ProjectWithTacheCount;
import com.taskflow.app.repository.ProjectRepository;

import java.util.List;

public class ProjectsViewModel extends AndroidViewModel {

    private ProjectRepository repository;
    private LiveData<List<ProjectWithTacheCount>> allProjects;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository(application);
        allProjects = repository.getAllProjectsWithTacheCount();
    }

    public LiveData<List<ProjectWithTacheCount>> getAllProjects() {
        return allProjects;
    }

    public void insert(Project project) {
        repository.insert(project);
    }

    public void update(Project project) {
        repository.update(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }
}
