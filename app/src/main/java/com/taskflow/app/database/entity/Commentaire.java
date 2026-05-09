package com.taskflow.app.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "commentaire", foreignKeys = {
        @ForeignKey(entity = Tache.class, parentColumns = "id", childColumns = "tache_id", onDelete = ForeignKey.CASCADE)
}, indices = { @Index("tache_id") })
public class Commentaire {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contenu")
    private String contenu;

    @ColumnInfo(name = "date_creation")
    private long dateCreation;

    @ColumnInfo(name = "tache_id")
    private int tacheId;

    public Commentaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getTacheId() {
        return tacheId;
    }

    public void setTacheId(int tacheId) {
        this.tacheId = tacheId;
    }
}
