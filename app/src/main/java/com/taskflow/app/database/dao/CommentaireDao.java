package com.taskflow.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taskflow.app.database.entity.Commentaire;

import java.util.List;

@Dao
public interface CommentaireDao {
    @Insert
    long insert(Commentaire commentaire);

    @Update
    void update(Commentaire commentaire);

    @Delete
    void delete(Commentaire commentaire);

    @Query("SELECT * FROM commentaire WHERE id = :id")
    LiveData<Commentaire> getById(int id);

    @Query("SELECT * FROM commentaire")
    LiveData<List<Commentaire>> getAll();

    @Query("SELECT * FROM commentaire WHERE tache_id = :tacheId ORDER BY date_creation DESC")
    LiveData<List<Commentaire>> getByTacheId(int tacheId);

    @Query("DELETE FROM commentaire")
    void deleteAll();
}
