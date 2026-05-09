package com.taskflow.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.taskflow.app.database.AppDatabase;
import com.taskflow.app.database.dao.CommentaireDao;
import com.taskflow.app.database.entity.Commentaire;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentaireRepository {
    private CommentaireDao commentaireDao;
    private LiveData<List<Commentaire>> allCommentaires;
    private ExecutorService executorService;

    public CommentaireRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        commentaireDao = database.commentaireDao();
        allCommentaires = commentaireDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Commentaire commentaire) {
        executorService.execute(() -> commentaireDao.insert(commentaire));
    }

    public void update(Commentaire commentaire) {
        executorService.execute(() -> commentaireDao.update(commentaire));
    }

    public void delete(Commentaire commentaire) {
        executorService.execute(() -> commentaireDao.delete(commentaire));
    }

    public void deleteAll() {
        executorService.execute(() -> commentaireDao.deleteAll());
    }

    public LiveData<Commentaire> getById(int id) {
        return commentaireDao.getById(id);
    }

    public LiveData<List<Commentaire>> getAllCommentaires() {
        return allCommentaires;
    }

    public LiveData<List<Commentaire>> getByTacheId(int tacheId) {
        return commentaireDao.getByTacheId(tacheId);
    }
}
