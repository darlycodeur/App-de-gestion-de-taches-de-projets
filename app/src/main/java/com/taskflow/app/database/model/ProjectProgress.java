package com.taskflow.app.database.model;

public class ProjectProgress {
    private int projectId;
    private String projectName;
    private int totalTasks;
    private int completedTasks;

    public ProjectProgress() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getProgress() {
        if (totalTasks == 0) return 0;
        return (int) ((completedTasks * 100.0f) / totalTasks);
    }
}
