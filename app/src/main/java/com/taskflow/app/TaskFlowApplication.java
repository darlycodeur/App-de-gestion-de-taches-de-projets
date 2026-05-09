package com.taskflow.app;

import android.app.Application;

import com.taskflow.app.database.AppDatabase;

public class TaskFlowApplication extends Application {

    private static TaskFlowApplication instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = AppDatabase.getInstance(this);
    }

    public static TaskFlowApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}