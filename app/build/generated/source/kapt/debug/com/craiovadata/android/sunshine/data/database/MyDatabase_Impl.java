package com.craiovadata.android.sunshine.data.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabase_Impl extends MyDatabase {
  private volatile WeatherDao _weatherDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `weather` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `weatherId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `temperature` REAL NOT NULL, `humidity` REAL NOT NULL, `pressure` REAL NOT NULL, `wind` REAL NOT NULL, `degrees` REAL NOT NULL, `lat` REAL NOT NULL, `lon` REAL NOT NULL, `iconCodeOWM` TEXT NOT NULL, `isCurrentWeather` INTEGER NOT NULL, `cityName` TEXT NOT NULL, `description` TEXT NOT NULL, `sunrise` INTEGER NOT NULL, `sunset` INTEGER NOT NULL, `dt` INTEGER NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_weather_date` ON `weather` (`date`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `webcams` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `updateDate` INTEGER NOT NULL, `previewUrl` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_webcams_id` ON `webcams` (`id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '88f3b2e51ceebdce20184a8737e16c70')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `weather`");
        _db.execSQL("DROP TABLE IF EXISTS `webcams`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWeather = new HashMap<String, TableInfo.Column>(17);
        _columnsWeather.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("weatherId", new TableInfo.Column("weatherId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("humidity", new TableInfo.Column("humidity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("pressure", new TableInfo.Column("pressure", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("wind", new TableInfo.Column("wind", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("degrees", new TableInfo.Column("degrees", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("lat", new TableInfo.Column("lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("lon", new TableInfo.Column("lon", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("iconCodeOWM", new TableInfo.Column("iconCodeOWM", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("isCurrentWeather", new TableInfo.Column("isCurrentWeather", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("sunrise", new TableInfo.Column("sunrise", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("sunset", new TableInfo.Column("sunset", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("dt", new TableInfo.Column("dt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeather = new HashSet<TableInfo.Index>(1);
        _indicesWeather.add(new TableInfo.Index("index_weather_date", true, Arrays.asList("date")));
        final TableInfo _infoWeather = new TableInfo("weather", _columnsWeather, _foreignKeysWeather, _indicesWeather);
        final TableInfo _existingWeather = TableInfo.read(_db, "weather");
        if (! _infoWeather.equals(_existingWeather)) {
          return new RoomOpenHelper.ValidationResult(false, "weather(com.craiovadata.android.sunshine.ui.models.WeatherEntry).\n"
                  + " Expected:\n" + _infoWeather + "\n"
                  + " Found:\n" + _existingWeather);
        }
        final HashMap<String, TableInfo.Column> _columnsWebcams = new HashMap<String, TableInfo.Column>(4);
        _columnsWebcams.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWebcams.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWebcams.put("updateDate", new TableInfo.Column("updateDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWebcams.put("previewUrl", new TableInfo.Column("previewUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWebcams = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWebcams = new HashSet<TableInfo.Index>(1);
        _indicesWebcams.add(new TableInfo.Index("index_webcams_id", true, Arrays.asList("id")));
        final TableInfo _infoWebcams = new TableInfo("webcams", _columnsWebcams, _foreignKeysWebcams, _indicesWebcams);
        final TableInfo _existingWebcams = TableInfo.read(_db, "webcams");
        if (! _infoWebcams.equals(_existingWebcams)) {
          return new RoomOpenHelper.ValidationResult(false, "webcams(com.craiovadata.android.sunshine.ui.models.WebcamEntry).\n"
                  + " Expected:\n" + _infoWebcams + "\n"
                  + " Found:\n" + _existingWebcams);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "88f3b2e51ceebdce20184a8737e16c70", "64260c6bd016037234bd2d9564b8563f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "weather","webcams");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `weather`");
      _db.execSQL("DELETE FROM `webcams`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public WeatherDao weatherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }
}
