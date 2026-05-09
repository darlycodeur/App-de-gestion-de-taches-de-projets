package com.taskflow.app.database.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.entity.Tache;

public class TaskWithProject {
    @Embedded
    public Tache tache;

    @Relation(parentColumn = "projet_id", entityColumn = "id")
    public Project project;
}
