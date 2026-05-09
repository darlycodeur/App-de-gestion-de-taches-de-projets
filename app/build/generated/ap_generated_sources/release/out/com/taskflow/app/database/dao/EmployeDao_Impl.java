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
import com.taskflow.app.database.entity.Employe;
import com.taskflow.app.database.model.EmployeWithTacheCount;
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
public final class EmployeDao_Impl implements EmployeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Employe> __insertionAdapterOfEmploye;

  private final EntityDeletionOrUpdateAdapter<Employe> __deletionAdapterOfEmploye;

  private final EntityDeletionOrUpdateAdapter<Employe> __updateAdapterOfEmploye;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public EmployeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmploye = new EntityInsertionAdapter<Employe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `employe` (`id`,`nom`,`prenom`,`email`,`avatar`,`num_matricule`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Employe entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getPrenom() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPrenom());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getAvatar() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAvatar());
        }
        if (entity.getNumMatricule() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNumMatricule());
        }
      }
    };
    this.__deletionAdapterOfEmploye = new EntityDeletionOrUpdateAdapter<Employe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `employe` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Employe entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEmploye = new EntityDeletionOrUpdateAdapter<Employe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `employe` SET `id` = ?,`nom` = ?,`prenom` = ?,`email` = ?,`avatar` = ?,`num_matricule` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Employe entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getPrenom() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPrenom());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getAvatar() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAvatar());
        }
        if (entity.getNumMatricule() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNumMatricule());
        }
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM employe";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Employe employe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfEmploye.insertAndReturnId(employe);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Employe employe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEmploye.handle(employe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Employe employe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEmploye.handle(employe);
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
  public LiveData<Employe> getById(final int id) {
    final String _sql = "SELECT * FROM employe WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"employe"}, false, new Callable<Employe>() {
      @Override
      @Nullable
      public Employe call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
          final Employe _result;
          if (_cursor.moveToFirst()) {
            _result = new Employe();
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
            final String _tmpPrenom;
            if (_cursor.isNull(_cursorIndexOfPrenom)) {
              _tmpPrenom = null;
            } else {
              _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
            }
            _result.setPrenom(_tmpPrenom);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _result.setAvatar(_tmpAvatar);
            final String _tmpNumMatricule;
            if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
              _tmpNumMatricule = null;
            } else {
              _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
            }
            _result.setNumMatricule(_tmpNumMatricule);
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
  public Employe getByIdSync(final int id) {
    final String _sql = "SELECT * FROM employe WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
      final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
      final Employe _result;
      if (_cursor.moveToFirst()) {
        _result = new Employe();
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
        final String _tmpPrenom;
        if (_cursor.isNull(_cursorIndexOfPrenom)) {
          _tmpPrenom = null;
        } else {
          _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
        }
        _result.setPrenom(_tmpPrenom);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final String _tmpAvatar;
        if (_cursor.isNull(_cursorIndexOfAvatar)) {
          _tmpAvatar = null;
        } else {
          _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
        }
        _result.setAvatar(_tmpAvatar);
        final String _tmpNumMatricule;
        if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
          _tmpNumMatricule = null;
        } else {
          _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
        }
        _result.setNumMatricule(_tmpNumMatricule);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Employe>> getAll() {
    final String _sql = "SELECT * FROM employe";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"employe"}, false, new Callable<List<Employe>>() {
      @Override
      @Nullable
      public List<Employe> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
          final List<Employe> _result = new ArrayList<Employe>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Employe _item;
            _item = new Employe();
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
            final String _tmpPrenom;
            if (_cursor.isNull(_cursorIndexOfPrenom)) {
              _tmpPrenom = null;
            } else {
              _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
            }
            _item.setPrenom(_tmpPrenom);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _item.setEmail(_tmpEmail);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _item.setAvatar(_tmpAvatar);
            final String _tmpNumMatricule;
            if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
              _tmpNumMatricule = null;
            } else {
              _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
            }
            _item.setNumMatricule(_tmpNumMatricule);
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
  public LiveData<Employe> getByEmail(final String email) {
    final String _sql = "SELECT * FROM employe WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"employe"}, false, new Callable<Employe>() {
      @Override
      @Nullable
      public Employe call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
          final Employe _result;
          if (_cursor.moveToFirst()) {
            _result = new Employe();
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
            final String _tmpPrenom;
            if (_cursor.isNull(_cursorIndexOfPrenom)) {
              _tmpPrenom = null;
            } else {
              _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
            }
            _result.setPrenom(_tmpPrenom);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _result.setAvatar(_tmpAvatar);
            final String _tmpNumMatricule;
            if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
              _tmpNumMatricule = null;
            } else {
              _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
            }
            _result.setNumMatricule(_tmpNumMatricule);
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
  public LiveData<Employe> getByNumMatricule(final String numMatricule) {
    final String _sql = "SELECT * FROM employe WHERE num_matricule = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (numMatricule == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, numMatricule);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"employe"}, false, new Callable<Employe>() {
      @Override
      @Nullable
      public Employe call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
          final Employe _result;
          if (_cursor.moveToFirst()) {
            _result = new Employe();
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
            final String _tmpPrenom;
            if (_cursor.isNull(_cursorIndexOfPrenom)) {
              _tmpPrenom = null;
            } else {
              _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
            }
            _result.setPrenom(_tmpPrenom);
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            _result.setEmail(_tmpEmail);
            final String _tmpAvatar;
            if (_cursor.isNull(_cursorIndexOfAvatar)) {
              _tmpAvatar = null;
            } else {
              _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
            }
            _result.setAvatar(_tmpAvatar);
            final String _tmpNumMatricule;
            if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
              _tmpNumMatricule = null;
            } else {
              _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
            }
            _result.setNumMatricule(_tmpNumMatricule);
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
  public LiveData<List<EmployeWithTacheCount>> getEmployeWithTacheCount() {
    final String _sql = "SELECT e.*, COUNT(t.id) as tacheCount FROM employe e LEFT JOIN tache t ON e.id = t.assigne_a_id GROUP BY e.id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"employe",
        "tache"}, false, new Callable<List<EmployeWithTacheCount>>() {
      @Override
      @Nullable
      public List<EmployeWithTacheCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfPrenom = CursorUtil.getColumnIndexOrThrow(_cursor, "prenom");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
          final int _cursorIndexOfNumMatricule = CursorUtil.getColumnIndexOrThrow(_cursor, "num_matricule");
          final int _cursorIndexOfTacheCount = CursorUtil.getColumnIndexOrThrow(_cursor, "tacheCount");
          final List<EmployeWithTacheCount> _result = new ArrayList<EmployeWithTacheCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmployeWithTacheCount _item;
            final Employe _tmpEmploye;
            if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfNom) && _cursor.isNull(_cursorIndexOfPrenom) && _cursor.isNull(_cursorIndexOfEmail) && _cursor.isNull(_cursorIndexOfAvatar) && _cursor.isNull(_cursorIndexOfNumMatricule))) {
              _tmpEmploye = new Employe();
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              _tmpEmploye.setId(_tmpId);
              final String _tmpNom;
              if (_cursor.isNull(_cursorIndexOfNom)) {
                _tmpNom = null;
              } else {
                _tmpNom = _cursor.getString(_cursorIndexOfNom);
              }
              _tmpEmploye.setNom(_tmpNom);
              final String _tmpPrenom;
              if (_cursor.isNull(_cursorIndexOfPrenom)) {
                _tmpPrenom = null;
              } else {
                _tmpPrenom = _cursor.getString(_cursorIndexOfPrenom);
              }
              _tmpEmploye.setPrenom(_tmpPrenom);
              final String _tmpEmail;
              if (_cursor.isNull(_cursorIndexOfEmail)) {
                _tmpEmail = null;
              } else {
                _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
              }
              _tmpEmploye.setEmail(_tmpEmail);
              final String _tmpAvatar;
              if (_cursor.isNull(_cursorIndexOfAvatar)) {
                _tmpAvatar = null;
              } else {
                _tmpAvatar = _cursor.getString(_cursorIndexOfAvatar);
              }
              _tmpEmploye.setAvatar(_tmpAvatar);
              final String _tmpNumMatricule;
              if (_cursor.isNull(_cursorIndexOfNumMatricule)) {
                _tmpNumMatricule = null;
              } else {
                _tmpNumMatricule = _cursor.getString(_cursorIndexOfNumMatricule);
              }
              _tmpEmploye.setNumMatricule(_tmpNumMatricule);
            } else {
              _tmpEmploye = null;
            }
            _item = new EmployeWithTacheCount();
            _item.tacheCount = _cursor.getInt(_cursorIndexOfTacheCount);
            _item.employe = _tmpEmploye;
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
