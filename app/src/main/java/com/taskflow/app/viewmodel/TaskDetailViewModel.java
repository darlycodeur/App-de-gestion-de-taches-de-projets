package com.taskflow.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taskflow.app.database.entity.Commentaire;
import com.taskflow.app.database.model.TacheDetail;
import com.taskflow.app.repository.CommentaireRepository;
import com.taskflow.app.repository.TacheRepository;

import java.util.List;

public class TaskDetailViewModel extends AndroidViewModel {

    private final TacheRepository tacheRepository;
    private final CommentaireRepository commentaireRepository;

    public TaskDetailViewModel(@NonNull Application application) {
        super(application);
        tacheRepository = new TacheRepository(application);
        commentaireRepository = new CommentaireRepository(application);
    }

    public LiveData<TacheDetail> getTacheDetail(int id) {
        return tacheRepository.getTacheDetailById(id);
    }

    public LiveData<List<Commentaire>> getCommentaires(int tacheId) {
        return commentaireRepository.getByTacheId(tacheId);
    }

    public void insertCommentaire(Commentaire commentaire) {
        commentaireRepository.insert(commentaire);
    }

    public void deleteCommentaire(Commentaire commentaire) {
        commentaireRepository.delete(commentaire);
    }
}
