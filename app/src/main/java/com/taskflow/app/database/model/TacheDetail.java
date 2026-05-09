package com.taskflow.app.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.taskflow.app.database.entity.Tache;

/** Tâche enrichie avec les noms des relations (projet, employé, catégorie). */
public class TacheDetail {
    @Embedded
    public Tache tache;

    @ColumnInfo(name = "projet_nom")
    public String projetNom;

    @ColumnInfo(name = "employe_nom")
    public String employeNom;

    @ColumnInfo(name = "employe_prenom")
    public String employePrenom;

    @ColumnInfo(name = "categorie_nom")
    public String categorieNom;

    @ColumnInfo(name = "categorie_couleur")
    public String categorieCouleur;

    public String getEmployeFullName() {
        if (employeNom == null) return "Non assigné";
        return employeNom + " " + (employePrenom != null ? employePrenom : "");
    }
}
