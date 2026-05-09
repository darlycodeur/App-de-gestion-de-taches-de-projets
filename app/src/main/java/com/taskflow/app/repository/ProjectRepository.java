package com.taskflow.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taskflow.app.database.AppDatabase;
import com.taskflow.app.database.dao.ProjectDao;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.model.ProjectWithTacheCount;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;
    private ExecutorService executorService;

    public ProjectRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        projectDao = database.projectDao();
        allProjects = projectDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Project project) {
        executorService.execute(() -> projectDao.insert(project));
    }

    public void update(Project project) {
        executorService.execute(() -> projectDao.update(project));
    }

    public void delete(Project project) {
        executorService.execute(() -> projectDao.delete(project));
    }

    public void deleteAll() {
        executorService.execute(() -> projectDao.deleteAll());
    }

    public LiveData<Project> getById(int id) {
        return projectDao.getById(id);
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<List<ProjectWithTacheCount>> getAllProjectsWithTacheCount() {
        return projectDao.getAllProjectsWithTacheCount();
    }
}
