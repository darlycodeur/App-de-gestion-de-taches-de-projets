package com.taskflow.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taskflow.app.database.entity.Categorie;

import java.util.List;

@Dao
public interface CategorieDao {
    @Insert
    long insert(Categorie categorie);

    @Update
    void update(Categorie categorie);

    @Delete
    void delete(Categorie categorie);

    @Query("SELECT * FROM categorie WHERE id = :id")
    LiveData<Categorie> getById(int id);

    @Query("SELECT * FROM categorie")
    LiveData<List<Categorie>> getAll();

    @Query("SELECT * FROM categorie WHERE projet_id = :projetId")
    LiveData<List<Categorie>> getByProjetId(int projetId);

    @Query("DELETE FROM categorie")
    void deleteAll();

    @Query("SELECT * FROM categorie WHERE projet_id = :projetId")
    List<Categorie> getByProjetIdSync(int projetId);
}
