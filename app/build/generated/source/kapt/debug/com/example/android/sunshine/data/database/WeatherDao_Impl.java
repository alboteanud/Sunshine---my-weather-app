package com.example.android.sunshine.data.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWeatherEntry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldWeather;

  public WeatherDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntry = new EntityInsertionAdapter<WeatherEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `weather`(`id`,`weatherId`,`date`,`temperature`,`humidity`,`pressure`,`wind`,`degrees`,`lat`,`lon`,`iconCodeOWM`,`isCurrentWeather`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WeatherEntry value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getWeatherId());
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindDouble(4, value.getTemperature());
        stmt.bindDouble(5, value.getHumidity());
        stmt.bindDouble(6, value.getPressure());
        stmt.bindDouble(7, value.getWind());
        stmt.bindDouble(8, value.getDegrees());
        stmt.bindDouble(9, value.getLat());
        stmt.bindDouble(10, value.getLon());
        if (value.getIconCodeOWM() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getIconCodeOWM());
        }
        stmt.bindLong(12, value.isCurrentWeather());
      }
    };
    this.__preparedStmtOfDeleteOldWeather = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM weather WHERE date < ?";
        return _query;
      }
    };
  }

  @Override
  public void bulkInsert(WeatherEntry... weather) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWeatherEntry.insert(weather);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteOldWeather(Date recently) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldWeather.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      final Long _tmp;
      _tmp = DateConverter.toTimestamp(recently);
      if (_tmp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, _tmp);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldWeather.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ListWeatherEntry>> getCurrentForecast(Date date) {
    final String _sql = "SELECT id, weatherId, date, temperature, iconCodeOWM FROM weather WHERE date >= ? ORDER BY date ASC LIMIT 5 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<List<ListWeatherEntry>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<ListWeatherEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfWeatherId = _cursor.getColumnIndexOrThrow("weatherId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemperature = _cursor.getColumnIndexOrThrow("temperature");
          final int _cursorIndexOfIconCodeOWM = _cursor.getColumnIndexOrThrow("iconCodeOWM");
          final List<ListWeatherEntry> _result = new ArrayList<ListWeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListWeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherId;
            _tmpWeatherId = _cursor.getInt(_cursorIndexOfWeatherId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final String _tmpIconCodeOWM;
            _tmpIconCodeOWM = _cursor.getString(_cursorIndexOfIconCodeOWM);
            _item = new ListWeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpIconCodeOWM);
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
    }.getLiveData();
  }

  @Override
  public int countAllFutureWeatherEntries(Date date) {
    final String _sql = "SELECT * FROM weather WHERE date > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countCurrentWeather(Date recently) {
    final String _sql = "SELECT * FROM weather WHERE date >= ? AND isCurrentWeather = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(recently);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<WeatherEntry>> getCurrentWeather(Date nowDate, Date recentlyDate) {
    final String _sql = "SELECT * FROM weather WHERE date  >= ? ORDER BY isCurrentWeather DESC, ABS(? - date) ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(recentlyDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1;
    _tmp_1 = DateConverter.toTimestamp(nowDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return new ComputableLiveData<List<WeatherEntry>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<WeatherEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfWeatherId = _cursor.getColumnIndexOrThrow("weatherId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemperature = _cursor.getColumnIndexOrThrow("temperature");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfLat = _cursor.getColumnIndexOrThrow("lat");
          final int _cursorIndexOfLon = _cursor.getColumnIndexOrThrow("lon");
          final int _cursorIndexOfIconCodeOWM = _cursor.getColumnIndexOrThrow("iconCodeOWM");
          final int _cursorIndexOfIsCurrentWeather = _cursor.getColumnIndexOrThrow("isCurrentWeather");
          final List<WeatherEntry> _result = new ArrayList<WeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherId;
            _tmpWeatherId = _cursor.getInt(_cursorIndexOfWeatherId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_2);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final String _tmpIconCodeOWM;
            _tmpIconCodeOWM = _cursor.getString(_cursorIndexOfIconCodeOWM);
            final int _tmpIsCurrentWeather;
            _tmpIsCurrentWeather = _cursor.getInt(_cursorIndexOfIsCurrentWeather);
            _item = new WeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIconCodeOWM,_tmpIsCurrentWeather);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final double _tmpLon;
            _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
            _item.setLon(_tmpLon);
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
    }.getLiveData();
  }

  @Override
  public LiveData<List<WeatherEntry>> getAllEntries() {
    final String _sql = "SELECT * FROM weather";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<WeatherEntry>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<WeatherEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfWeatherId = _cursor.getColumnIndexOrThrow("weatherId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemperature = _cursor.getColumnIndexOrThrow("temperature");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfLat = _cursor.getColumnIndexOrThrow("lat");
          final int _cursorIndexOfLon = _cursor.getColumnIndexOrThrow("lon");
          final int _cursorIndexOfIconCodeOWM = _cursor.getColumnIndexOrThrow("iconCodeOWM");
          final int _cursorIndexOfIsCurrentWeather = _cursor.getColumnIndexOrThrow("isCurrentWeather");
          final List<WeatherEntry> _result = new ArrayList<WeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherId;
            _tmpWeatherId = _cursor.getInt(_cursorIndexOfWeatherId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final String _tmpIconCodeOWM;
            _tmpIconCodeOWM = _cursor.getString(_cursorIndexOfIconCodeOWM);
            final int _tmpIsCurrentWeather;
            _tmpIsCurrentWeather = _cursor.getInt(_cursorIndexOfIsCurrentWeather);
            _item = new WeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIconCodeOWM,_tmpIsCurrentWeather);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final double _tmpLon;
            _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
            _item.setLon(_tmpLon);
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
    }.getLiveData();
  }

  @Override
  public LiveData<List<ListWeatherEntry>> getDaysForecast(Date tomorrowCityNoonUtc, long offset,
      long hourInMillis) {
    final String _sql = "SELECT * FROM weather WHERE date > ? AND (date + ?) % (24 * ?) BETWEEN (11 * ? +1) AND 14 * ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(tomorrowCityNoonUtc);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, offset);
    _argIndex = 3;
    _statement.bindLong(_argIndex, hourInMillis);
    _argIndex = 4;
    _statement.bindLong(_argIndex, hourInMillis);
    _argIndex = 5;
    _statement.bindLong(_argIndex, hourInMillis);
    return new ComputableLiveData<List<ListWeatherEntry>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<ListWeatherEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfWeatherId = _cursor.getColumnIndexOrThrow("weatherId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemperature = _cursor.getColumnIndexOrThrow("temperature");
          final int _cursorIndexOfIconCodeOWM = _cursor.getColumnIndexOrThrow("iconCodeOWM");
          final List<ListWeatherEntry> _result = new ArrayList<ListWeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListWeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherId;
            _tmpWeatherId = _cursor.getInt(_cursorIndexOfWeatherId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
            final String _tmpIconCodeOWM;
            _tmpIconCodeOWM = _cursor.getString(_cursorIndexOfIconCodeOWM);
            _item = new ListWeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpIconCodeOWM);
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
    }.getLiveData();
  }
}
