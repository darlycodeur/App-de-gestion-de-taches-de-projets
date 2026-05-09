package com.taskflow.app.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.taskflow.app.database.entity.Commentaire;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CommentaireDao_Impl implements CommentaireDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Commentaire> __insertionAdapterOfCommentaire;

  private final EntityDeletionOrUpdateAdapter<Commentaire> __deletionAdapterOfCommentaire;

  private final EntityDeletionOrUpdateAdapter<Commentaire> __updateAdapterOfCommentaire;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CommentaireDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCommentaire = new EntityInsertionAdapter<Commentaire>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `commentaire` (`id`,`contenu`,`date_creation`,`tache_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Commentaire entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getContenu() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getContenu());
        }
        statement.bindLong(3, entity.getDateCreation());
        statement.bindLong(4, entity.getTacheId());
      }
    };
    this.__deletionAdapterOfCommentaire = new EntityDeletionOrUpdateAdapter<Commentaire>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `commentaire` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Commentaire entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCommentaire = new EntityDeletionOrUpdateAdapter<Commentaire>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `commentaire` SET `id` = ?,`contenu` = ?,`date_creation` = ?,`tache_id` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Commentaire entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getContenu() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getContenu());
        }
        statement.bindLong(3, entity.getDateCreation());
        statement.bindLong(4, entity.getTacheId());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM commentaire";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Commentaire commentaire) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCommentaire.insertAndReturnId(commentaire);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Commentaire commentaire) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCommentaire.handle(commentaire);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Commentaire commentaire) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCommentaire.handle(commentaire);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<Commentaire> getById(final int id) {
    final String _sql = "SELECT * FROM commentaire WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"commentaire"}, false, new Callable<Commentaire>() {
      @Override
      @Nullable
      public Commentaire call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContenu = CursorUtil.getColumnIndexOrThrow(_cursor, "contenu");
          final int _cursorIndexOfDateCreation = CursorUtil.getColumnIndexOrThrow(_cursor, "date_creation");
          final int _cursorIndexOfTacheId = CursorUtil.getColumnIndexOrThrow(_cursor, "tache_id");
          final Commentaire _result;
          if (_cursor.moveToFirst()) {
            _result = new Commentaire();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpContenu;
            if (_cursor.isNull(_cursorIndexOfContenu)) {
              _tmpContenu = null;
            } else {
              _tmpContenu = _cursor.getString(_cursorIndexOfContenu);
            }
            _result.setContenu(_tmpContenu);
            final long _tmpDateCreation;
            _tmpDateCreation = _cursor.getLong(_cursorIndexOfDateCreation);
            _result.setDateCreation(_tmpDateCreation);
            final int _tmpTacheId;
            _tmpTacheId = _cursor.getInt(_cursorIndexOfTacheId);
            _result.setTacheId(_tmpTacheId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Commentaire>> getAll() {
    final String _sql = "SELECT * FROM commentaire";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"commentaire"}, false, new Callable<List<Commentaire>>() {
      @Override
      @Nullable
      public List<Commentaire> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContenu = CursorUtil.getColumnIndexOrThrow(_cursor, "contenu");
          final int _cursorIndexOfDateCreation = CursorUtil.getColumnIndexOrThrow(_cursor, "date_creation");
          final int _cursorIndexOfTacheId = CursorUtil.getColumnIndexOrThrow(_cursor, "tache_id");
          final List<Commentaire> _result = new ArrayList<Commentaire>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Commentaire _item;
            _item = new Commentaire();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpContenu;
            if (_cursor.isNull(_cursorIndexOfContenu)) {
              _tmpContenu = null;
            } else {
              _tmpContenu = _cursor.getString(_cursorIndexOfContenu);
            }
            _item.setContenu(_tmpContenu);
            final long _tmpDateCreation;
            _tmpDateCreation = _cursor.getLong(_cursorIndexOfDateCreation);
            _item.setDateCreation(_tmpDateCreation);
            final int _tmpTacheId;
            _tmpTacheId = _cursor.getInt(_cursorIndexOfTacheId);
            _item.setTacheId(_tmpTacheId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Commentaire>> getByTacheId(final int tacheId) {
    final String _sql = "SELECT * FROM commentaire WHERE tache_id = ? ORDER BY date_creation DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tacheId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"commentaire"}, false, new Callable<List<Commentaire>>() {
      @Override
      @Nullable
      public List<Commentaire> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContenu = CursorUtil.getColumnIndexOrThrow(_cursor, "contenu");
          final int _cursorIndexOfDateCreation = CursorUtil.getColumnIndexOrThrow(_cursor, "date_creation");
          final int _cursorIndexOfTacheId = CursorUtil.getColumnIndexOrThrow(_cursor, "tache_id");
          final List<Commentaire> _result = new ArrayList<Commentaire>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Commentaire _item;
            _item = new Commentaire();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpContenu;
            if (_cursor.isNull(_cursorIndexOfContenu)) {
              _tmpContenu = null;
            } else {
              _tmpContenu = _cursor.getString(_cursorIndexOfContenu);
            }
            _item.setContenu(_tmpContenu);
            final long _tmpDateCreation;
            _tmpDateCreation = _cursor.getLong(_cursorIndexOfDateCreation);
            _item.setDateCreation(_tmpDateCreation);
            final int _tmpTacheId;
            _tmpTacheId = _cursor.getInt(_cursorIndexOfTacheId);
            _item.setTacheId(_tmpTacheId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
