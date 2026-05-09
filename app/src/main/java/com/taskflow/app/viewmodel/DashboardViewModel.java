package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.EmployeWithTacheCount;
import com.taskflow.app.database.model.ProjectProgress;
import com.taskflow.app.database.model.TaskWithProject;
import com.taskflow.app.repository.EmployeRepository;
import com.taskflow.app.repository.ProjectRepository;
import com.taskflow.app.repository.TacheRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardViewModel extends AndroidViewModel {

    private TacheRepository tacheRepository;
    private ProjectRepository projectRepository;
    private EmployeRepository employeRepository;

    private LiveData<Integer> totalProjects;
    private LiveData<Integer> tachesTodo;
    private LiveData<Integer> tachesInProgress;
    private LiveData<Integer> tachesCompleted;
    private LiveData<Integer> tachesOverdue;
    private LiveData<List<EmployeWithTacheCount>> employeeWorkload;
    private LiveData<List<TaskWithProject>> recentTasks;
    private LiveData<List<ProjectProgress>> projectsProgress;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        tacheRepository = new TacheRepository(application);
        projectRepository = new ProjectRepository(application);
        employeRepository = new EmployeRepository(application);

        totalProjects = Transformations.map(
                projectRepository.getAllProjects(),
                projects -> projects != null ? projects.size() : 0
        );

        tachesTodo = tacheRepository.countByStatut("à faire");
        tachesInProgress = tacheRepository.countByStatut("en cours");
        tachesCompleted = tacheRepository.countByStatut("terminée");

        tachesOverdue = Transformations.map(
                tacheRepository.getTachesEnRetard(System.currentTimeMillis()),
                tasks -> tasks != null ? tasks.size() : 0
        );

        employeeWorkload = employeRepository.getEmployeWithTacheCount();

        LiveData<List<Tache>> allTaches = tacheRepository.getAllTaches();
        LiveData<List<Project>> allProjects = projectRepository.getAllProjects();

        MediatorLiveData<List<TaskWithProject>> recentTasksMediator = new MediatorLiveData<>();
        recentTasksMediator.addSource(allTaches, taches ->
                combineRecentTasks(taches, allProjects.getValue(), recentTasksMediator));
        recentTasksMediator.addSource(allProjects, projects ->
                combineRecentTasks(allTaches.getValue(), projects, recentTasksMediator));
        recentTasks = recentTasksMediator;

        MediatorLiveData<List<ProjectProgress>> projectsProgressMediator = new MediatorLiveData<>();
        projectsProgressMediator.addSource(allTaches, taches ->
                combineProjectsProgress(taches, allProjects.getValue(), projectsProgressMediator));
        projectsProgressMediator.addSource(allProjects, projects ->
                combineProjectsProgress(allTaches.getValue(), projects, projectsProgressMediator));
        projectsProgress = projectsProgressMediator;
    }

    private void combineRecentTasks(List<Tache> taches, List<Project> projects,
                                    MediatorLiveData<List<TaskWithProject>> mediator) {
        if (taches == null || projects == null) {
            mediator.setValue(null);
            return;
        }
        Map<Integer, Project> projectMap = new HashMap<>();
        for (Project p : projects) {
            projectMap.put(p.getId(), p);
        }
        List<TaskWithProject> result = new ArrayList<>();
        for (Tache t : taches) {
            TaskWithProject twp = new TaskWithProject();
            twp.tache = t;
            twp.project = projectMap.get(t.getProjetId());
            result.add(twp);
        }
        mediator.setValue(result);
    }

    private void combineProjectsProgress(List<Tache> taches, List<Project> projects,
                                         MediatorLiveData<List<ProjectProgress>> mediator) {
        if (taches == null || projects == null) {
            mediator.setValue(null);
            return;
        }
        Map<Integer, int[]> progressMap = new HashMap<>();
        for (Tache t : taches) {
            int[] counts = progressMap.get(t.getProjetId());
            if (counts == null) {
                counts = new int[2];
                progressMap.put(t.getProjetId(), counts);
            }
            counts[0]++;
            if ("terminée".equals(t.getStatut())) {
                counts[1]++;
            }
        }
        List<ProjectProgress> result = new ArrayList<>();
        for (Project p : projects) {
            int[] counts = progressMap.get(p.getId());
            ProjectProgress pp = new ProjectProgress();
            pp.setProjectId(p.getId());
            pp.setProjectName(p.getNom());
            pp.setTotalTasks(counts != null ? counts[0] : 0);
            pp.setCompletedTasks(counts != null ? counts[1] : 0);
            result.add(pp);
        }
        mediator.setValue(result);
    }

    public void insertEmployee(com.taskflow.app.database.entity.Employe employe) {
        employeRepository.insert(employe);
    }

    public void updateEmployee(com.taskflow.app.database.entity.Employe employe) {
        employeRepository.update(employe);
    }

    public void deleteEmployee(com.taskflow.app.database.entity.Employe employe) {
        employeRepository.delete(employe);
    }

    public LiveData<Integer> getTotalProjects() { return totalProjects; }
    public LiveData<Integer> getTachesTodo() { return tachesTodo; }
    public LiveData<Integer> getTachesInProgress() { return tachesInProgress; }
    public LiveData<Integer> getTachesCompleted() { return tachesCompleted; }
    public LiveData<Integer> getTachesOverdue() { return tachesOverdue; }
    public LiveData<List<EmployeWithTacheCount>> getEmployeeWorkload() { return employeeWorkload; }
    public LiveData<List<TaskWithProject>> getRecentTasks() { return recentTasks; }
    public LiveData<List<ProjectProgress>> getProjectsProgress() { return projectsProgress; }
}
