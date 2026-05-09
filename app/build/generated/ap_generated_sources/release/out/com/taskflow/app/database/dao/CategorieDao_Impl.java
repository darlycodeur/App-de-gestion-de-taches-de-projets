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
import com.taskflow.app.database.entity.Categorie;
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
public final class CategorieDao_Impl implements CategorieDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Categorie> __insertionAdapterOfCategorie;

  private final EntityDeletionOrUpdateAdapter<Categorie> __deletionAdapterOfCategorie;

  private final EntityDeletionOrUpdateAdapter<Categorie> __updateAdapterOfCategorie;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CategorieDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategorie = new EntityInsertionAdapter<Categorie>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `categorie` (`id`,`nom`,`couleur`,`projet_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Categorie entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getCouleur() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCouleur());
        }
        statement.bindLong(4, entity.getProjetId());
      }
    };
    this.__deletionAdapterOfCategorie = new EntityDeletionOrUpdateAdapter<Categorie>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `categorie` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Categorie entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCategorie = new EntityDeletionOrUpdateAdapter<Categorie>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `categorie` SET `id` = ?,`nom` = ?,`couleur` = ?,`projet_id` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Categorie entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getCouleur() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCouleur());
        }
        statement.bindLong(4, entity.getProjetId());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM categorie";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Categorie categorie) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCategorie.insertAndReturnId(categorie);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Categorie categorie) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCategorie.handle(categorie);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Categorie categorie) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCategorie.handle(categorie);
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
  public LiveData<Categorie> getById(final int id) {
    final String _sql = "SELECT * FROM categorie WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categorie"}, false, new Callable<Categorie>() {
      @Override
      @Nullable
      public Categorie call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "couleur");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final Categorie _result;
          if (_cursor.moveToFirst()) {
            _result = new Categorie();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpNom;
            if (_cursor.isNull(_cursorIndexOfNom)) {
              _tmpNom = null;
            } else {
              _tmpNom = _cursor.getString(_cursorIndexOfNom);
            }
            _result.setNom(_tmpNom);
            final String _tmpCouleur;
            if (_cursor.isNull(_cursorIndexOfCouleur)) {
              _tmpCouleur = null;
            } else {
              _tmpCouleur = _cursor.getString(_cursorIndexOfCouleur);
            }
            _result.setCouleur(_tmpCouleur);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _result.setProjetId(_tmpProjetId);
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
  public LiveData<List<Categorie>> getAll() {
    final String _sql = "SELECT * FROM categorie";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categorie"}, false, new Callable<List<Categorie>>() {
      @Override
      @Nullable
      public List<Categorie> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "couleur");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final List<Categorie> _result = new ArrayList<Categorie>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Categorie _item;
            _item = new Categorie();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpNom;
            if (_cursor.isNull(_cursorIndexOfNom)) {
              _tmpNom = null;
            } else {
              _tmpNom = _cursor.getString(_cursorIndexOfNom);
            }
            _item.setNom(_tmpNom);
            final String _tmpCouleur;
            if (_cursor.isNull(_cursorIndexOfCouleur)) {
              _tmpCouleur = null;
            } else {
              _tmpCouleur = _cursor.getString(_cursorIndexOfCouleur);
            }
            _item.setCouleur(_tmpCouleur);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
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
  public LiveData<List<Categorie>> getByProjetId(final int projetId) {
    final String _sql = "SELECT * FROM categorie WHERE projet_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projetId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"categorie"}, false, new Callable<List<Categorie>>() {
      @Override
      @Nullable
      public List<Categorie> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "couleur");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final List<Categorie> _result = new ArrayList<Categorie>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Categorie _item;
            _item = new Categorie();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpNom;
            if (_cursor.isNull(_cursorIndexOfNom)) {
              _tmpNom = null;
            } else {
              _tmpNom = _cursor.getString(_cursorIndexOfNom);
            }
            _item.setNom(_tmpNom);
            final String _tmpCouleur;
            if (_cursor.isNull(_cursorIndexOfCouleur)) {
              _tmpCouleur = null;
            } else {
              _tmpCouleur = _cursor.getString(_cursorIndexOfCouleur);
            }
            _item.setCouleur(_tmpCouleur);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
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
  public List<Categorie> getByProjetIdSync(final int projetId) {
    final String _sql = "SELECT * FROM categorie WHERE projet_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projetId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
      final int _cursorIndexOfCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "couleur");
      final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
      final List<Categorie> _result = new ArrayList<Categorie>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Categorie _item;
        _item = new Categorie();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpNom;
        if (_cursor.isNull(_cursorIndexOfNom)) {
          _tmpNom = null;
        } else {
          _tmpNom = _cursor.getString(_cursorIndexOfNom);
        }
        _item.setNom(_tmpNom);
        final String _tmpCouleur;
        if (_cursor.isNull(_cursorIndexOfCouleur)) {
          _tmpCouleur = null;
        } else {
          _tmpCouleur = _cursor.getString(_cursorIndexOfCouleur);
        }
        _item.setCouleur(_tmpCouleur);
        final int _tmpProjetId;
        _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
        _item.setProjetId(_tmpProjetId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
