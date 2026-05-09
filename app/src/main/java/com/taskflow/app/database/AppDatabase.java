package com.taskflow.app.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.taskflow.app.database.dao.CategorieDao;
import com.taskflow.app.database.dao.CommentaireDao;
import com.taskflow.app.database.dao.EmployeDao;
import com.taskflow.app.database.dao.ProjectDao;
import com.taskflow.app.database.dao.TacheDao;
import com.taskflow.app.database.entity.Categorie;
import com.taskflow.app.database.entity.Commentaire;
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.entity.Project;
import com.taskflow.app.database.entity.Tache;

@Database(entities = {
        Employe.class,
        Project.class,
        Categorie.class,
        Tache.class,
        Commentaire.class
}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract EmployeDao employeDao();
    public abstract ProjectDao projectDao();
    public abstract CategorieDao categorieDao();
    public abstract TacheDao tacheDao();
    public abstract CommentaireDao commentaireDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "taskflow_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    populateInitialData();
                                }

                                @Override
                                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                                    super.onDestructiveMigration(db);
                                    populateInitialData();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void populateInitialData() {
        new Thread(() -> {
            Employe emp1 = new Employe();
            emp1.setNom("Dupont");
            emp1.setPrenom("Jean");
            emp1.setEmail("jean.dupont@taskflow.com");
            emp1.setNumMatricule("EMP001");
            long emp1Id = INSTANCE.employeDao().insert(emp1);

            Employe emp2 = new Employe();
            emp2.setNom("Martin");
            emp2.setPrenom("Marie");
            emp2.setEmail("marie.martin@taskflow.com");
            emp2.setNumMatricule("EMP002");
            long emp2Id = INSTANCE.employeDao().insert(emp2);

            Employe emp3 = new Employe();
            emp3.setNom("Bernard");
            emp3.setPrenom("Pierre");
            emp3.setEmail("pierre.bernard@taskflow.com");
            emp3.setNumMatricule("EMP003");
            long emp3Id = INSTANCE.employeDao().insert(emp3);

            Project proj1 = new Project();
            proj1.setNom("Application Mobile");
            proj1.setDescription("Développement d'une application mobile de gestion de tâches");
            proj1.setDateCreation(System.currentTimeMillis());
            long proj1Id = INSTANCE.projectDao().insert(proj1);

            Project proj2 = new Project();
            proj2.setNom("Site Web");
            proj2.setDescription("Création d'un site web corporate");
            proj2.setDateCreation(System.currentTimeMillis());
            long proj2Id = INSTANCE.projectDao().insert(proj2);

            Categorie cat1 = new Categorie();
            cat1.setNom("Design");
            cat1.setCouleur("#1976D2");
            cat1.setProjetId((int) proj1Id);
            INSTANCE.categorieDao().insert(cat1);

            Categorie cat2 = new Categorie();
            cat2.setNom("Backend");
            cat2.setCouleur("#388E3C");
            cat2.setProjetId((int) proj1Id);
            INSTANCE.categorieDao().insert(cat2);

            Categorie cat3 = new Categorie();
            cat3.setNom("Frontend");
            cat3.setCouleur("#F57C00");
            cat3.setProjetId((int) proj2Id);
            INSTANCE.categorieDao().insert(cat3);

            long now = System.currentTimeMillis();
            long day = 24 * 60 * 60 * 1000L;

            Tache t1 = new Tache();
            t1.setTitre("Design de l'interface");
            t1.setDescription("Créer les maquettes de l'application");
            t1.setDateEcheance(now + 7 * day);
            t1.setPriorite(4);
            t1.setStatut("en cours");
            t1.setProjetId((int) proj1Id);
            t1.setAssigneAId((int) emp1Id);
            INSTANCE.tacheDao().insert(t1);

            Tache t2 = new Tache();
            t2.setTitre("Développement backend");
            t2.setDescription("API REST pour la gestion des données");
            t2.setDateEcheance(now + 14 * day);
            t2.setPriorite(5);
            t2.setStatut("à faire");
            t2.setProjetId((int) proj1Id);
            t2.setAssigneAId((int) emp2Id);
            INSTANCE.tacheDao().insert(t2);

            Tache t3 = new Tache();
            t3.setTitre("Tests unitaires");
            t3.setDescription("Écriture des tests pour l'application");
            t3.setDateEcheance(now - 2 * day);
            t3.setPriorite(3);
            t3.setStatut("à faire");
            t3.setProjetId((int) proj1Id);
            t3.setAssigneAId((int) emp3Id);
            INSTANCE.tacheDao().insert(t3);

            Tache t4 = new Tache();
            t4.setTitre("Analyse des besoins");
            t4.setDescription("Définir les spécifications du site");
            t4.setDateEcheance(now + 5 * day);
            t4.setPriorite(2);
            t4.setStatut("terminée");
            t4.setProjetId((int) proj2Id);
            t4.setAssigneAId((int) emp1Id);
            INSTANCE.tacheDao().insert(t4);

            Tache t5 = new Tache();
            t5.setTitre("Intégration graphique");
            t5.setDescription("Intégrer les éléments visuels du site");
            t5.setDateEcheance(now + 10 * day);
            t5.setPriorite(3);
            t5.setStatut("en cours");
            t5.setProjetId((int) proj2Id);
            t5.setAssigneAId((int) emp2Id);
            INSTANCE.tacheDao().insert(t5);
        }).start();
    }
}
