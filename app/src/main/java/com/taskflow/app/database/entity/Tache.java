package com.taskflow.app.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tache", foreignKeys = {
        @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projet_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Employe.class, parentColumns = "id", childColumns = "assigne_a_id", onDelete = ForeignKey.SET_NULL),
        @ForeignKey(entity = Categorie.class, parentColumns = "id", childColumns = "categorie_id", onDelete = ForeignKey.SET_NULL)
}, indices = { @Index("projet_id"), @Index("assigne_a_id"), @Index("categorie_id") })
public class Tache {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titre")
    private String titre;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date_echeance")
    private long dateEcheance;

    @ColumnInfo(name = "priorite")
    private int priorite;

    @ColumnInfo(name = "statut")
    private String statut;

    @ColumnInfo(name = "projet_id")
    private int projetId;

    @ColumnInfo(name = "assigne_a_id")
    private Integer assigneAId;

    @ColumnInfo(name = "categorie_id")
    private Integer categorieId;

    public Tache() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(long dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getProjetId() {
        return projetId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }

    public Integer getAssigneAId() {
        return assigneAId;
    }

    public void setAssigneAId(Integer assigneAId) {
        this.assigneAId = assigneAId;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }
}
