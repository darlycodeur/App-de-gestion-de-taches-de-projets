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
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.database.model.TacheDetail;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TacheDao_Impl implements TacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tache> __insertionAdapterOfTache;

  private final EntityDeletionOrUpdateAdapter<Tache> __deletionAdapterOfTache;

  private final EntityDeletionOrUpdateAdapter<Tache> __updateAdapterOfTache;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TacheDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTache = new EntityInsertionAdapter<Tache>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tache` (`id`,`titre`,`description`,`date_echeance`,`priorite`,`statut`,`projet_id`,`assigne_a_id`,`categorie_id`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tache entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitre() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitre());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        statement.bindLong(4, entity.getDateEcheance());
        statement.bindLong(5, entity.getPriorite());
        if (entity.getStatut() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getStatut());
        }
        statement.bindLong(7, entity.getProjetId());
        if (entity.getAssigneAId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getAssigneAId());
        }
        if (entity.getCategorieId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getCategorieId());
        }
      }
    };
    this.__deletionAdapterOfTache = new EntityDeletionOrUpdateAdapter<Tache>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tache` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tache entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTache = new EntityDeletionOrUpdateAdapter<Tache>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tache` SET `id` = ?,`titre` = ?,`description` = ?,`date_echeance` = ?,`priorite` = ?,`statut` = ?,`projet_id` = ?,`assigne_a_id` = ?,`categorie_id` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tache entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitre() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitre());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        statement.bindLong(4, entity.getDateEcheance());
        statement.bindLong(5, entity.getPriorite());
        if (entity.getStatut() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getStatut());
        }
        statement.bindLong(7, entity.getProjetId());
        if (entity.getAssigneAId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getAssigneAId());
        }
        if (entity.getCategorieId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getCategorieId());
        }
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tache";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Tache tache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTache.insertAndReturnId(tache);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Tache tache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTache.handle(tache);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Tache tache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTache.handle(tache);
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
  public LiveData<Tache> getById(final int id) {
    final String _sql = "SELECT * FROM tache WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<Tache>() {
      @Override
      @Nullable
      public Tache call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final Tache _result;
          if (_cursor.moveToFirst()) {
            _result = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _result.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _result.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _result.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _result.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _result.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _result.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _result.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _result.setCategorieId(_tmpCategorieId);
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
  public LiveData<List<Tache>> getAll() {
    final String _sql = "SELECT * FROM tache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<List<Tache>>() {
      @Override
      @Nullable
      public List<Tache> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final List<Tache> _result = new ArrayList<Tache>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tache _item;
            _item = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _item.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _item.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _item.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _item.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _item.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _item.setCategorieId(_tmpCategorieId);
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
  public LiveData<List<Tache>> getTachesByProjet(final int projetId) {
    final String _sql = "SELECT * FROM tache WHERE projet_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projetId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<List<Tache>>() {
      @Override
      @Nullable
      public List<Tache> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final List<Tache> _result = new ArrayList<Tache>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tache _item;
            _item = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _item.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _item.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _item.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _item.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _item.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _item.setCategorieId(_tmpCategorieId);
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
  public LiveData<List<Tache>> getTachesByEmploye(final int employeId) {
    final String _sql = "SELECT * FROM tache WHERE assigne_a_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, employeId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<List<Tache>>() {
      @Override
      @Nullable
      public List<Tache> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final List<Tache> _result = new ArrayList<Tache>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tache _item;
            _item = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _item.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _item.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _item.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _item.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _item.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _item.setCategorieId(_tmpCategorieId);
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
  public LiveData<List<Tache>> getTachesByStatut(final String statut) {
    final String _sql = "SELECT * FROM tache WHERE statut = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (statut == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, statut);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<List<Tache>>() {
      @Override
      @Nullable
      public List<Tache> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final List<Tache> _result = new ArrayList<Tache>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tache _item;
            _item = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _item.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _item.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _item.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _item.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _item.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _item.setCategorieId(_tmpCategorieId);
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
  public LiveData<List<Tache>> getTachesEnRetard(final long currentTime) {
    final String _sql = "SELECT * FROM tache WHERE date_echeance < ? AND statut != 'terminée'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, currentTime);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<List<Tache>>() {
      @Override
      @Nullable
      public List<Tache> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final List<Tache> _result = new ArrayList<Tache>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tache _item;
            _item = new Tache();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitre;
            if (_cursor.isNull(_cursorIndexOfTitre)) {
              _tmpTitre = null;
            } else {
              _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
            }
            _item.setTitre(_tmpTitre);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final long _tmpDateEcheance;
            _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
            _item.setDateEcheance(_tmpDateEcheance);
            final int _tmpPriorite;
            _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
            _item.setPriorite(_tmpPriorite);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            _item.setStatut(_tmpStatut);
            final int _tmpProjetId;
            _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
            _item.setProjetId(_tmpProjetId);
            final Integer _tmpAssigneAId;
            if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
              _tmpAssigneAId = null;
            } else {
              _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
            }
            _item.setAssigneAId(_tmpAssigneAId);
            final Integer _tmpCategorieId;
            if (_cursor.isNull(_cursorIndexOfCategorieId)) {
              _tmpCategorieId = null;
            } else {
              _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
            }
            _item.setCategorieId(_tmpCategorieId);
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
  public LiveData<Integer> countByStatut(final String statut) {
    final String _sql = "SELECT COUNT(*) FROM tache WHERE statut = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (statut == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, statut);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public LiveData<Integer> countByProjet(final int projetId) {
    final String _sql = "SELECT COUNT(*) FROM tache WHERE projet_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, projetId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public LiveData<TacheDetail> getTacheDetailById(final int id) {
    final String _sql = "SELECT t.*, p.nom AS projet_nom, e.nom AS employe_nom, e.prenom AS employe_prenom, c.nom AS categorie_nom, c.couleur AS categorie_couleur FROM tache t LEFT JOIN project p ON t.projet_id = p.id LEFT JOIN employe e ON t.assigne_a_id = e.id LEFT JOIN categorie c ON t.categorie_id = c.id WHERE t.id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache", "project", "employe",
        "categorie"}, false, new Callable<TacheDetail>() {
      @Override
      @Nullable
      public TacheDetail call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final int _cursorIndexOfProjetNom = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_nom");
          final int _cursorIndexOfEmployeNom = CursorUtil.getColumnIndexOrThrow(_cursor, "employe_nom");
          final int _cursorIndexOfEmployePrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "employe_prenom");
          final int _cursorIndexOfCategorieNom = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_nom");
          final int _cursorIndexOfCategorieCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_couleur");
          final TacheDetail _result;
          if (_cursor.moveToFirst()) {
            final Tache _tmpTache;
            if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitre) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfDateEcheance) && _cursor.isNull(_cursorIndexOfPriorite) && _cursor.isNull(_cursorIndexOfStatut) && _cursor.isNull(_cursorIndexOfProjetId) && _cursor.isNull(_cursorIndexOfAssigneAId) && _cursor.isNull(_cursorIndexOfCategorieId))) {
              _tmpTache = new Tache();
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              _tmpTache.setId(_tmpId);
              final String _tmpTitre;
              if (_cursor.isNull(_cursorIndexOfTitre)) {
                _tmpTitre = null;
              } else {
                _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
              }
              _tmpTache.setTitre(_tmpTitre);
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              _tmpTache.setDescription(_tmpDescription);
              final long _tmpDateEcheance;
              _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
              _tmpTache.setDateEcheance(_tmpDateEcheance);
              final int _tmpPriorite;
              _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
              _tmpTache.setPriorite(_tmpPriorite);
              final String _tmpStatut;
              if (_cursor.isNull(_cursorIndexOfStatut)) {
                _tmpStatut = null;
              } else {
                _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
              }
              _tmpTache.setStatut(_tmpStatut);
              final int _tmpProjetId;
              _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
              _tmpTache.setProjetId(_tmpProjetId);
              final Integer _tmpAssigneAId;
              if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
                _tmpAssigneAId = null;
              } else {
                _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
              }
              _tmpTache.setAssigneAId(_tmpAssigneAId);
              final Integer _tmpCategorieId;
              if (_cursor.isNull(_cursorIndexOfCategorieId)) {
                _tmpCategorieId = null;
              } else {
                _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
              }
              _tmpTache.setCategorieId(_tmpCategorieId);
            } else {
              _tmpTache = null;
            }
            _result = new TacheDetail();
            if (_cursor.isNull(_cursorIndexOfProjetNom)) {
              _result.projetNom = null;
            } else {
              _result.projetNom = _cursor.getString(_cursorIndexOfProjetNom);
            }
            if (_cursor.isNull(_cursorIndexOfEmployeNom)) {
              _result.employeNom = null;
            } else {
              _result.employeNom = _cursor.getString(_cursorIndexOfEmployeNom);
            }
            if (_cursor.isNull(_cursorIndexOfEmployePrenom)) {
              _result.employePrenom = null;
            } else {
              _result.employePrenom = _cursor.getString(_cursorIndexOfEmployePrenom);
            }
            if (_cursor.isNull(_cursorIndexOfCategorieNom)) {
              _result.categorieNom = null;
            } else {
              _result.categorieNom = _cursor.getString(_cursorIndexOfCategorieNom);
            }
            if (_cursor.isNull(_cursorIndexOfCategorieCouleur)) {
              _result.categorieCouleur = null;
            } else {
              _result.categorieCouleur = _cursor.getString(_cursorIndexOfCategorieCouleur);
            }
            _result.tache = _tmpTache;
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
  public LiveData<List<TacheDetail>> getTacheDetailsByEmploye(final int employeId) {
    final String _sql = "SELECT t.*, p.nom AS projet_nom, e.nom AS employe_nom, e.prenom AS employe_prenom, c.nom AS categorie_nom, c.couleur AS categorie_couleur FROM tache t LEFT JOIN project p ON t.projet_id = p.id LEFT JOIN employe e ON t.assigne_a_id = e.id LEFT JOIN categorie c ON t.categorie_id = c.id WHERE t.assigne_a_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, employeId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tache", "project", "employe",
        "categorie"}, false, new Callable<List<TacheDetail>>() {
      @Override
      @Nullable
      public List<TacheDetail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitre = CursorUtil.getColumnIndexOrThrow(_cursor, "titre");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDateEcheance = CursorUtil.getColumnIndexOrThrow(_cursor, "date_echeance");
          final int _cursorIndexOfPriorite = CursorUtil.getColumnIndexOrThrow(_cursor, "priorite");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfProjetId = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_id");
          final int _cursorIndexOfAssigneAId = CursorUtil.getColumnIndexOrThrow(_cursor, "assigne_a_id");
          final int _cursorIndexOfCategorieId = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_id");
          final int _cursorIndexOfProjetNom = CursorUtil.getColumnIndexOrThrow(_cursor, "projet_nom");
          final int _cursorIndexOfEmployeNom = CursorUtil.getColumnIndexOrThrow(_cursor, "employe_nom");
          final int _cursorIndexOfEmployePrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "employe_prenom");
          final int _cursorIndexOfCategorieNom = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_nom");
          final int _cursorIndexOfCategorieCouleur = CursorUtil.getColumnIndexOrThrow(_cursor, "categorie_couleur");
          final List<TacheDetail> _result = new ArrayList<TacheDetail>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TacheDetail _item;
            final Tache _tmpTache;
            if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitre) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfDateEcheance) && _cursor.isNull(_cursorIndexOfPriorite) && _cursor.isNull(_cursorIndexOfStatut) && _cursor.isNull(_cursorIndexOfProjetId) && _cursor.isNull(_cursorIndexOfAssigneAId) && _cursor.isNull(_cursorIndexOfCategorieId))) {
              _tmpTache = new Tache();
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              _tmpTache.setId(_tmpId);
              final String _tmpTitre;
              if (_cursor.isNull(_cursorIndexOfTitre)) {
                _tmpTitre = null;
              } else {
                _tmpTitre = _cursor.getString(_cursorIndexOfTitre);
              }
              _tmpTache.setTitre(_tmpTitre);
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              _tmpTache.setDescription(_tmpDescription);
              final long _tmpDateEcheance;
              _tmpDateEcheance = _cursor.getLong(_cursorIndexOfDateEcheance);
              _tmpTache.setDateEcheance(_tmpDateEcheance);
              final int _tmpPriorite;
              _tmpPriorite = _cursor.getInt(_cursorIndexOfPriorite);
              _tmpTache.setPriorite(_tmpPriorite);
              final String _tmpStatut;
              if (_cursor.isNull(_cursorIndexOfStatut)) {
                _tmpStatut = null;
              } else {
                _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
              }
              _tmpTache.setStatut(_tmpStatut);
              final int _tmpProjetId;
              _tmpProjetId = _cursor.getInt(_cursorIndexOfProjetId);
              _tmpTache.setProjetId(_tmpProjetId);
              final Integer _tmpAssigneAId;
              if (_cursor.isNull(_cursorIndexOfAssigneAId)) {
                _tmpAssigneAId = null;
              } else {
                _tmpAssigneAId = _cursor.getInt(_cursorIndexOfAssigneAId);
              }
              _tmpTache.setAssigneAId(_tmpAssigneAId);
              final Integer _tmpCategorieId;
              if (_cursor.isNull(_cursorIndexOfCategorieId)) {
                _tmpCategorieId = null;
              } else {
                _tmpCategorieId = _cursor.getInt(_cursorIndexOfCategorieId);
              }
              _tmpTache.setCategorieId(_tmpCategorieId);
            } else {
              _tmpTache = null;
            }
            _item = new TacheDetail();
            if (_cursor.isNull(_cursorIndexOfProjetNom)) {
              _item.projetNom = null;
            } else {
              _item.projetNom = _cursor.getString(_cursorIndexOfProjetNom);
            }
            if (_cursor.isNull(_cursorIndexOfEmployeNom)) {
              _item.employeNom = null;
            } else {
              _item.employeNom = _cursor.getString(_cursorIndexOfEmployeNom);
            }
            if (_cursor.isNull(_cursorIndexOfEmployePrenom)) {
              _item.employePrenom = null;
            } else {
              _item.employePrenom = _cursor.getString(_cursorIndexOfEmployePrenom);
            }
            if (_cursor.isNull(_cursorIndexOfCategorieNom)) {
              _item.categorieNom = null;
            } else {
              _item.categorieNom = _cursor.getString(_cursorIndexOfCategorieNom);
            }
            if (_cursor.isNull(_cursorIndexOfCategorieCouleur)) {
              _item.categorieCouleur = null;
            } else {
              _item.categorieCouleur = _cursor.getString(_cursorIndexOfCategorieCouleur);
            }
            _item.tache = _tmpTache;
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
