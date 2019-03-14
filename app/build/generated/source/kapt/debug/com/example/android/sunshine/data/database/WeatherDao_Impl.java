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
        return "INSERT OR REPLACE INTO `weather`(`id`,`weatherIconId`,`date`,`temp`,`humidity`,`pressure`,`wind`,`degrees`,`icon`,`isCurrentWeather`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WeatherEntry value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getWeatherIconId());
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindDouble(4, value.getTemp());
        stmt.bindDouble(5, value.getHumidity());
        stmt.bindDouble(6, value.getPressure());
        stmt.bindDouble(7, value.getWind());
        stmt.bindDouble(8, value.getDegrees());
        if (value.getIcon() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getIcon());
        }
        stmt.bindLong(10, value.isCurrentWeather());
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
  public void deleteOldWeather(Date date) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldWeather.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      final Long _tmp;
      _tmp = DateConverter.toTimestamp(date);
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
  public LiveData<List<ListWeatherEntry>> getCurrentWeatherForecasts(Date date) {
    final String _sql = "SELECT id, weatherIconId, date, `temp`, icon FROM weather WHERE date >= ? LIMIT 6";
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
          final int _cursorIndexOfWeatherIconId = _cursor.getColumnIndexOrThrow("weatherIconId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemp = _cursor.getColumnIndexOrThrow("temp");
          final int _cursorIndexOfIcon = _cursor.getColumnIndexOrThrow("icon");
          final List<ListWeatherEntry> _result = new ArrayList<ListWeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListWeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherIconId;
            _tmpWeatherIconId = _cursor.getInt(_cursorIndexOfWeatherIconId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpTemp;
            _tmpTemp = _cursor.getDouble(_cursorIndexOfTemp);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            _item = new ListWeatherEntry(_tmpId,_tmpWeatherIconId,_tmpDate,_tmpTemp,_tmpIcon);
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
  public int countAllFutureWeather(Date date) {
    final String _sql = "SELECT * FROM weather WHERE date = ?";
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
  public LiveData<WeatherEntry> getWeatherByDate(Date date) {
    final String _sql = "SELECT * FROM weather WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<WeatherEntry>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected WeatherEntry compute() {
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
          final int _cursorIndexOfWeatherIconId = _cursor.getColumnIndexOrThrow("weatherIconId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemp = _cursor.getColumnIndexOrThrow("temp");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfIcon = _cursor.getColumnIndexOrThrow("icon");
          final int _cursorIndexOfIsCurrentWeather = _cursor.getColumnIndexOrThrow("isCurrentWeather");
          final WeatherEntry _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherIconId;
            _tmpWeatherIconId = _cursor.getInt(_cursorIndexOfWeatherIconId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpTemp;
            _tmpTemp = _cursor.getDouble(_cursorIndexOfTemp);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final int _tmpIsCurrentWeather;
            _tmpIsCurrentWeather = _cursor.getInt(_cursorIndexOfIsCurrentWeather);
            _result = new WeatherEntry(_tmpId,_tmpWeatherIconId,_tmpDate,_tmpTemp,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIcon,_tmpIsCurrentWeather);
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
    }.getLiveData();
  }

  @Override
  public Date getLastUpdatedDateCW(Date date) {
    final String _sql = "SELECT date FROM weather WHERE date > ? AND isCurrentWeather = 1";
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
      final Date _result;
      if(_cursor.moveToFirst()) {
        final Long _tmp_1;
        if (_cursor.isNull(0)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(0);
        }
        _result = DateConverter.toDate(_tmp_1);
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
  public LiveData<WeatherEntry> getCurrentWeather(Date date) {
    final String _sql = "SELECT id, weatherIconId, date, `temp`, icon, humidity, pressure, wind, degrees, isCurrentWeather FROM weather WHERE date >= ? ORDER BY isCurrentWeather DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<WeatherEntry>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected WeatherEntry compute() {
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
          final int _cursorIndexOfWeatherIconId = _cursor.getColumnIndexOrThrow("weatherIconId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTemp = _cursor.getColumnIndexOrThrow("temp");
          final int _cursorIndexOfIcon = _cursor.getColumnIndexOrThrow("icon");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfIsCurrentWeather = _cursor.getColumnIndexOrThrow("isCurrentWeather");
          final WeatherEntry _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpWeatherIconId;
            _tmpWeatherIconId = _cursor.getInt(_cursorIndexOfWeatherIconId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpTemp;
            _tmpTemp = _cursor.getDouble(_cursorIndexOfTemp);
            final String _tmpIcon;
            _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final int _tmpIsCurrentWeather;
            _tmpIsCurrentWeather = _cursor.getInt(_cursorIndexOfIsCurrentWeather);
            _result = new WeatherEntry(_tmpId,_tmpWeatherIconId,_tmpDate,_tmpTemp,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIcon,_tmpIsCurrentWeather);
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
    }.getLiveData();
  }
}
