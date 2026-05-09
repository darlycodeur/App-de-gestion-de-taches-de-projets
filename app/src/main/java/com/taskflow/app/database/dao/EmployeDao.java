package com.taskflow.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.model.EmployeWithTacheCount;

import java.util.List;

@Dao
public interface EmployeDao {
    @Insert
    long insert(Employe employe);

    @Update
    void update(Employe employe);

    @Delete
    void delete(Employe employe);

    @Query("SELECT * FROM employe WHERE id = :id")
    LiveData<Employe> getById(int id);

    @Query("SELECT * FROM employe WHERE id = :id")
    Employe getByIdSync(int id);

    @Query("SELECT * FROM employe")
    LiveData<List<Employe>> getAll();

    @Query("SELECT * FROM employe WHERE email = :email")
    LiveData<Employe> getByEmail(String email);

    @Query("SELECT * FROM employe WHERE num_matricule = :numMatricule")
    LiveData<Employe> getByNumMatricule(String numMatricule);

    @Query("DELETE FROM employe")
    void deleteAll();

    @Query("SELECT e.*, COUNT(t.id) as tacheCount FROM employe e LEFT JOIN tache t ON e.id = t.assigne_a_id GROUP BY e.id")
    LiveData<List<EmployeWithTacheCount>> getEmployeWithTacheCount();
}
