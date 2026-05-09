package com.taskflow.app.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.taskflow.app.database.entity.Project;

public class ProjectWithTacheCount {
    @Embedded
    public Project project;

    public int tacheCount;

    @ColumnInfo(name = "completedCount")
    public int completedCount;

    public ProjectWithTacheCount() {
    }

    public int getProgress() {
        if (tacheCount == 0) return 0;
        return (int) ((completedCount * 100.0f) / tacheCount);
    }
}
