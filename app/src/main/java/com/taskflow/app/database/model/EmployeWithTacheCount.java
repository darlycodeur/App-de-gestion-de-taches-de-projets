package com.taskflow.app.database.model;

import androidx.room.Embedded;

import com.taskflow.app.database.entity.Employe;

public class EmployeWithTacheCount {
    @Embedded
    public Employe employe;

    public int tacheCount;

    public EmployeWithTacheCount() {
    }
}
