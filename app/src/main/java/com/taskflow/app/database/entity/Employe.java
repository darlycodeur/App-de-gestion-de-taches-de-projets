package com.taskflow.app.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "employe", indices = {
        @Index(value = "email", unique = true),
        @Index(value = "num_matricule", unique = true)
})
public class Employe {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "avatar")
    private String avatar;

    @ColumnInfo(name = "num_matricule")
    private String numMatricule;

    public Employe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNumMatricule() {
        return numMatricule;
    }

    public void setNumMatricule(String numMatricule) {
        this.numMatricule = numMatricule;
    }
}
