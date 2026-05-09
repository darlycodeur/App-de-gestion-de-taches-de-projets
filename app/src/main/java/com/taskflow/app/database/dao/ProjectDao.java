package com.taskflow.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.model.ProjectWithTacheCount;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert
    long insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    LiveData<Project> getById(int id);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAll();

    @Query("DELETE FROM project")
    void deleteAll();

    @Query("SELECT p.*, COUNT(t.id) as tacheCount, SUM(CASE WHEN t.statut = 'terminée' THEN 1 ELSE 0 END) as completedCount FROM project p LEFT JOIN tache t ON p.id = t.projet_id GROUP BY p.id")
    LiveData<List<ProjectWithTacheCount>> getAllProjectsWithTacheCount();
}
