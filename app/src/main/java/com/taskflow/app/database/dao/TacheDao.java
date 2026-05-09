package com.taskflow.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.TacheDetail;

import java.util.List;

@Dao
public interface TacheDao {
    @Insert
    long insert(Tache tache);

    @Update
    void update(Tache tache);

    @Delete
    void delete(Tache tache);

    @Query("SELECT * FROM tache WHERE id = :id")
    LiveData<Tache> getById(int id);

    @Query("SELECT * FROM tache")
    LiveData<List<Tache>> getAll();

    @Query("DELETE FROM tache")
    void deleteAll();

    @Query("SELECT * FROM tache WHERE projet_id = :projetId")
    LiveData<List<Tache>> getTachesByProjet(int projetId);

    @Query("SELECT * FROM tache WHERE assigne_a_id = :employeId")
    LiveData<List<Tache>> getTachesByEmploye(int employeId);

    @Query("SELECT * FROM tache WHERE statut = :statut")
    LiveData<List<Tache>> getTachesByStatut(String statut);

    @Query("SELECT * FROM tache WHERE date_echeance < :currentTime AND statut != 'terminée'")
    LiveData<List<Tache>> getTachesEnRetard(long currentTime);

    @Query("SELECT COUNT(*) FROM tache WHERE statut = :statut")
    LiveData<Integer> countByStatut(String statut);

    @Query("SELECT COUNT(*) FROM tache WHERE projet_id = :projetId")
    LiveData<Integer> countByProjet(int projetId);

    @Query("SELECT t.*, p.nom AS projet_nom, e.nom AS employe_nom, e.prenom AS employe_prenom, " +
            "c.nom AS categorie_nom, c.couleur AS categorie_couleur " +
            "FROM tache t " +
            "LEFT JOIN project p ON t.projet_id = p.id " +
            "LEFT JOIN employe e ON t.assigne_a_id = e.id " +
            "LEFT JOIN categorie c ON t.categorie_id = c.id " +
            "WHERE t.id = :id")
    LiveData<TacheDetail> getTacheDetailById(int id);

    @Query("SELECT t.*, p.nom AS projet_nom, e.nom AS employe_nom, e.prenom AS employe_prenom, " +
            "c.nom AS categorie_nom, c.couleur AS categorie_couleur " +
            "FROM tache t " +
            "LEFT JOIN project p ON t.projet_id = p.id " +
            "LEFT JOIN employe e ON t.assigne_a_id = e.id " +
            "LEFT JOIN categorie c ON t.categorie_id = c.id " +
            "WHERE t.assigne_a_id = :employeId")
    LiveData<List<TacheDetail>> getTacheDetailsByEmploye(int employeId);
}
