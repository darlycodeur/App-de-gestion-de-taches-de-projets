package com.taskflow.app.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.taskflow.app.database.dao.CategorieDao;
import com.taskflow.app.database.dao.CategorieDao_Impl;
import com.taskflow.app.database.dao.CommentaireDao;
import com.taskflow.app.database.dao.CommentaireDao_Impl;
import com.taskflow.app.database.dao.EmployeDao;
import com.taskflow.app.database.dao.EmployeDao_Impl;
import com.taskflow.app.database.dao.ProjectDao;
import com.taskflow.app.database.dao.ProjectDao_Impl;
import com.taskflow.app.database.dao.TacheDao;
import com.taskflow.app.database.dao.TacheDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EmployeDao _employeDao;

  private volatile ProjectDao _projectDao;

  private volatile CategorieDao _categorieDao;

  private volatile TacheDao _tacheDao;

  private volatile CommentaireDao _commentaireDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `employe` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT, `prenom` TEXT, `email` TEXT, `avatar` TEXT, `num_matricule` TEXT)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_employe_email` ON `employe` (`email`)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_employe_num_matricule` ON `employe` (`num_matricule`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `project` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT, `description` TEXT, `date_creation` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categorie` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT, `couleur` TEXT, `projet_id` INTEGER NOT NULL, FOREIGN KEY(`projet_id`) REFERENCES `project`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_categorie_projet_id` ON `categorie` (`projet_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tache` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titre` TEXT, `description` TEXT, `date_echeance` INTEGER NOT NULL, `priorite` INTEGER NOT NULL, `statut` TEXT, `projet_id` INTEGER NOT NULL, `assigne_a_id` INTEGER, `categorie_id` INTEGER, FOREIGN KEY(`projet_id`) REFERENCES `project`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`assigne_a_id`) REFERENCES `employe`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`categorie_id`) REFERENCES `categorie`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tache_projet_id` ON `tache` (`projet_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tache_assigne_a_id` ON `tache` (`assigne_a_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_tache_categorie_id` ON `tache` (`categorie_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `commentaire` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `contenu` TEXT, `date_creation` INTEGER NOT NULL, `tache_id` INTEGER NOT NULL, FOREIGN KEY(`tache_id`) REFERENCES `tache`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_commentaire_tache_id` ON `commentaire` (`tache_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '14f2e517e4022440978909b1dd4e08ed')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `employe`");
        db.execSQL("DROP TABLE IF EXISTS `project`");
        db.execSQL("DROP TABLE IF EXISTS `categorie`");
        db.execSQL("DROP TABLE IF EXISTS `tache`");
        db.execSQL("DROP TABLE IF EXISTS `commentaire`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsEmploye = new HashMap<String, TableInfo.Column>(6);
        _columnsEmploye.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmploye.put("nom", new TableInfo.Column("nom", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmploye.put("prenom", new TableInfo.Column("prenom", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmploye.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmploye.put("avatar", new TableInfo.Column("avatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEmploye.put("num_matricule", new TableInfo.Column("num_matricule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEmploye = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEmploye = new HashSet<TableInfo.Index>(2);
        _indicesEmploye.add(new TableInfo.Index("index_employe_email", true, Arrays.asList("email"), Arrays.asList("ASC")));
        _indicesEmploye.add(new TableInfo.Index("index_employe_num_matricule", true, Arrays.asList("num_matricule"), Arrays.asList("ASC")));
        final TableInfo _infoEmploye = new TableInfo("employe", _columnsEmploye, _foreignKeysEmploye, _indicesEmploye);
        final TableInfo _existingEmploye = TableInfo.read(db, "employe");
        if (!_infoEmploye.equals(_existingEmploye)) {
          return new RoomOpenHelper.ValidationResult(false, "employe(com.taskflow.app.database.entity.Employe).\n"
                  + " Expected:\n" + _infoEmploye + "\n"
                  + " Found:\n" + _existingEmploye);
        }
        final HashMap<String, TableInfo.Column> _columnsProject = new HashMap<String, TableInfo.Column>(4);
        _columnsProject.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProject.put("nom", new TableInfo.Column("nom", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProject.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProject.put("date_creation", new TableInfo.Column("date_creation", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProject = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProject = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProject = new TableInfo("project", _columnsProject, _foreignKeysProject, _indicesProject);
        final TableInfo _existingProject = TableInfo.read(db, "project");
        if (!_infoProject.equals(_existingProject)) {
          return new RoomOpenHelper.ValidationResult(false, "project(com.taskflow.app.database.entity.Project).\n"
                  + " Expected:\n" + _infoProject + "\n"
                  + " Found:\n" + _existingProject);
        }
        final HashMap<String, TableInfo.Column> _columnsCategorie = new HashMap<String, TableInfo.Column>(4);
        _columnsCategorie.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorie.put("nom", new TableInfo.Column("nom", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorie.put("couleur", new TableInfo.Column("couleur", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorie.put("projet_id", new TableInfo.Column("projet_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategorie = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCategorie.add(new TableInfo.ForeignKey("project", "CASCADE", "NO ACTION", Arrays.asList("projet_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCategorie = new HashSet<TableInfo.Index>(1);
        _indicesCategorie.add(new TableInfo.Index("index_categorie_projet_id", false, Arrays.asList("projet_id"), Arrays.asList("ASC")));
        final TableInfo _infoCategorie = new TableInfo("categorie", _columnsCategorie, _foreignKeysCategorie, _indicesCategorie);
        final TableInfo _existingCategorie = TableInfo.read(db, "categorie");
        if (!_infoCategorie.equals(_existingCategorie)) {
          return new RoomOpenHelper.ValidationResult(false, "categorie(com.taskflow.app.database.entity.Categorie).\n"
                  + " Expected:\n" + _infoCategorie + "\n"
                  + " Found:\n" + _existingCategorie);
        }
        final HashMap<String, TableInfo.Column> _columnsTache = new HashMap<String, TableInfo.Column>(9);
        _columnsTache.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("titre", new TableInfo.Column("titre", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("date_echeance", new TableInfo.Column("date_echeance", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("priorite", new TableInfo.Column("priorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("statut", new TableInfo.Column("statut", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("projet_id", new TableInfo.Column("projet_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("assigne_a_id", new TableInfo.Column("assigne_a_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTache.put("categorie_id", new TableInfo.Column("categorie_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTache = new HashSet<TableInfo.ForeignKey>(3);
        _foreignKeysTache.add(new TableInfo.ForeignKey("project", "CASCADE", "NO ACTION", Arrays.asList("projet_id"), Arrays.asList("id")));
        _foreignKeysTache.add(new TableInfo.ForeignKey("employe", "SET NULL", "NO ACTION", Arrays.asList("assigne_a_id"), Arrays.asList("id")));
        _foreignKeysTache.add(new TableInfo.ForeignKey("categorie", "SET NULL", "NO ACTION", Arrays.asList("categorie_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTache = new HashSet<TableInfo.Index>(3);
        _indicesTache.add(new TableInfo.Index("index_tache_projet_id", false, Arrays.asList("projet_id"), Arrays.asList("ASC")));
        _indicesTache.add(new TableInfo.Index("index_tache_assigne_a_id", false, Arrays.asList("assigne_a_id"), Arrays.asList("ASC")));
        _indicesTache.add(new TableInfo.Index("index_tache_categorie_id", false, Arrays.asList("categorie_id"), Arrays.asList("ASC")));
        final TableInfo _infoTache = new TableInfo("tache", _columnsTache, _foreignKeysTache, _indicesTache);
        final TableInfo _existingTache = TableInfo.read(db, "tache");
        if (!_infoTache.equals(_existingTache)) {
          return new RoomOpenHelper.ValidationResult(false, "tache(com.taskflow.app.database.entity.Tache).\n"
                  + " Expected:\n" + _infoTache + "\n"
                  + " Found:\n" + _existingTache);
        }
        final HashMap<String, TableInfo.Column> _columnsCommentaire = new HashMap<String, TableInfo.Column>(4);
        _columnsCommentaire.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCommentaire.put("contenu", new TableInfo.Column("contenu", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCommentaire.put("date_creation", new TableInfo.Column("date_creation", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCommentaire.put("tache_id", new TableInfo.Column("tache_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCommentaire = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCommentaire.add(new TableInfo.ForeignKey("tache", "CASCADE", "NO ACTION", Arrays.asList("tache_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCommentaire = new HashSet<TableInfo.Index>(1);
        _indicesCommentaire.add(new TableInfo.Index("index_commentaire_tache_id", false, Arrays.asList("tache_id"), Arrays.asList("ASC")));
        final TableInfo _infoCommentaire = new TableInfo("commentaire", _columnsCommentaire, _foreignKeysCommentaire, _indicesCommentaire);
        final TableInfo _existingCommentaire = TableInfo.read(db, "commentaire");
        if (!_infoCommentaire.equals(_existingCommentaire)) {
          return new RoomOpenHelper.ValidationResult(false, "commentaire(com.taskflow.app.database.entity.Commentaire).\n"
                  + " Expected:\n" + _infoCommentaire + "\n"
                  + " Found:\n" + _existingCommentaire);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "14f2e517e4022440978909b1dd4e08ed", "d93656218d37955d8907222776720d6d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "employe","project","categorie","tache","commentaire");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `employe`");
      _db.execSQL("DELETE FROM `project`");
      _db.execSQL("DELETE FROM `categorie`");
      _db.execSQL("DELETE FROM `tache`");
      _db.execSQL("DELETE FROM `commentaire`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EmployeDao.class, EmployeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProjectDao.class, ProjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategorieDao.class, CategorieDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TacheDao.class, TacheDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CommentaireDao.class, CommentaireDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public EmployeDao employeDao() {
    if (_employeDao != null) {
      return _employeDao;
    } else {
      synchronized(this) {
        if(_employeDao == null) {
          _employeDao = new EmployeDao_Impl(this);
        }
        return _employeDao;
      }
    }
  }

  @Override
  public ProjectDao projectDao() {
    if (_projectDao != null) {
      return _projectDao;
    } else {
      synchronized(this) {
        if(_projectDao == null) {
          _projectDao = new ProjectDao_Impl(this);
        }
        return _projectDao;
      }
    }
  }

  @Override
  public CategorieDao categorieDao() {
    if (_categorieDao != null) {
      return _categorieDao;
    } else {
      synchronized(this) {
        if(_categorieDao == null) {
          _categorieDao = new CategorieDao_Impl(this);
        }
        return _categorieDao;
      }
    }
  }

  @Override
  public TacheDao tacheDao() {
    if (_tacheDao != null) {
      return _tacheDao;
    } else {
      synchronized(this) {
        if(_tacheDao == null) {
          _tacheDao = new TacheDao_Impl(this);
        }
        return _tacheDao;
      }
    }
  }

  @Override
  public CommentaireDao commentaireDao() {
    if (_commentaireDao != null) {
      return _commentaireDao;
    } else {
      synchronized(this) {
        if(_commentaireDao == null) {
          _commentaireDao = new CommentaireDao_Impl(this);
        }
        return _commentaireDao;
      }
    }
  }
}
