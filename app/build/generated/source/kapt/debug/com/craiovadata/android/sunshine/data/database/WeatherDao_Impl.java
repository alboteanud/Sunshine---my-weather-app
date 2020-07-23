package com.craiovadata.android.sunshine.data.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.craiovadata.android.sunshine.ui.models.ListWeatherEntry;
import com.craiovadata.android.sunshine.ui.models.WeatherEntry;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntry> __insertionAdapterOfWeatherEntry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldWeather;

  public WeatherDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntry = new EntityInsertionAdapter<WeatherEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `weather` (`id`,`weatherId`,`date`,`temperature`,`humidity`,`pressure`,`wind`,`degrees`,`lat`,`lon`,`iconCodeOWM`,`isCurrentWeather`,`cityName`,`description`,`sunrise`,`sunset`,`dt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
        if (value.getCityName() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getCityName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getDescription());
        }
        stmt.bindLong(15, value.getSunrise());
        stmt.bindLong(16, value.getSunset());
        stmt.bindLong(17, value.getDt());
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
  public void bulkInsert(final WeatherEntry... weather) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWeatherEntry.insert(weather);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteOldWeather(final Date recently) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldWeather.acquire();
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(recently);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, _tmp);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldWeather.release(_stmt);
    }
  }

  @Override
  public LiveData<List<WeatherEntry>> getAllEntries() {
    final String _sql = "SELECT * FROM weather";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"weather"}, false, new Callable<List<WeatherEntry>>() {
      @Override
      public List<WeatherEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWeatherId = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfWind = CursorUtil.getColumnIndexOrThrow(_cursor, "wind");
          final int _cursorIndexOfDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "degrees");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLon = CursorUtil.getColumnIndexOrThrow(_cursor, "lon");
          final int _cursorIndexOfIconCodeOWM = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCodeOWM");
          final int _cursorIndexOfIsCurrentWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentWeather");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfDt = CursorUtil.getColumnIndexOrThrow(_cursor, "dt");
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
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            _item = new WeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIconCodeOWM,_tmpIsCurrentWeather,_tmpCityName);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final double _tmpLon;
            _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
            _item.setLon(_tmpLon);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final long _tmpSunrise;
            _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
            _item.setSunrise(_tmpSunrise);
            final long _tmpSunset;
            _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
            _item.setSunset(_tmpSunset);
            final long _tmpDt;
            _tmpDt = _cursor.getLong(_cursorIndexOfDt);
            _item.setDt(_tmpDt);
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
  public LiveData<List<ListWeatherEntry>> getCurrentForecast(final Date date) {
    final String _sql = "SELECT id, weatherId, date, temperature, iconCodeOWM FROM weather WHERE date >= ? ORDER BY date ASC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"weather"}, false, new Callable<List<ListWeatherEntry>>() {
      @Override
      public List<ListWeatherEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWeatherId = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfIconCodeOWM = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCodeOWM");
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
    });
  }

  @Override
  public int countAllFutureWeatherEntries(final Date date) {
    final String _sql = "SELECT COUNT(*) FROM weather WHERE date > ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
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
  public int countCurrentWeather(final Date recently) {
    final String _sql = "SELECT COUNT(*) FROM weather WHERE date >= ? AND isCurrentWeather = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(recently);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
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
  public LiveData<List<WeatherEntry>> getCurrentWeather(final Date recentlyDate, final int limit) {
    final String _sql = "SELECT * FROM weather WHERE date  >= ? ORDER BY isCurrentWeather DESC, date ASC LIMIT ?";
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
    _statement.bindLong(_argIndex, limit);
    return __db.getInvalidationTracker().createLiveData(new String[]{"weather"}, false, new Callable<List<WeatherEntry>>() {
      @Override
      public List<WeatherEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWeatherId = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfWind = CursorUtil.getColumnIndexOrThrow(_cursor, "wind");
          final int _cursorIndexOfDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "degrees");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLon = CursorUtil.getColumnIndexOrThrow(_cursor, "lon");
          final int _cursorIndexOfIconCodeOWM = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCodeOWM");
          final int _cursorIndexOfIsCurrentWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentWeather");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfDt = CursorUtil.getColumnIndexOrThrow(_cursor, "dt");
          final List<WeatherEntry> _result = new ArrayList<WeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WeatherEntry _item;
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
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            _item = new WeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIconCodeOWM,_tmpIsCurrentWeather,_tmpCityName);
            final double _tmpLat;
            _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
            _item.setLat(_tmpLat);
            final double _tmpLon;
            _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
            _item.setLon(_tmpLon);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final long _tmpSunrise;
            _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
            _item.setSunrise(_tmpSunrise);
            final long _tmpSunset;
            _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
            _item.setSunset(_tmpSunset);
            final long _tmpDt;
            _tmpDt = _cursor.getLong(_cursorIndexOfDt);
            _item.setDt(_tmpDt);
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
  public List<WeatherEntry> getCurrentWeatherList(final Date recentlyDate) {
    final String _sql = "SELECT * FROM weather WHERE date  >= ? ORDER BY isCurrentWeather DESC, date ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(recentlyDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfWeatherId = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
      final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
      final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
      final int _cursorIndexOfWind = CursorUtil.getColumnIndexOrThrow(_cursor, "wind");
      final int _cursorIndexOfDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "degrees");
      final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
      final int _cursorIndexOfLon = CursorUtil.getColumnIndexOrThrow(_cursor, "lon");
      final int _cursorIndexOfIconCodeOWM = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCodeOWM");
      final int _cursorIndexOfIsCurrentWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentWeather");
      final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
      final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
      final int _cursorIndexOfDt = CursorUtil.getColumnIndexOrThrow(_cursor, "dt");
      final List<WeatherEntry> _result = new ArrayList<WeatherEntry>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WeatherEntry _item;
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
        final String _tmpCityName;
        _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
        _item = new WeatherEntry(_tmpId,_tmpWeatherId,_tmpDate,_tmpTemperature,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees,_tmpIconCodeOWM,_tmpIsCurrentWeather,_tmpCityName);
        final double _tmpLat;
        _tmpLat = _cursor.getDouble(_cursorIndexOfLat);
        _item.setLat(_tmpLat);
        final double _tmpLon;
        _tmpLon = _cursor.getDouble(_cursorIndexOfLon);
        _item.setLon(_tmpLon);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final long _tmpSunrise;
        _tmpSunrise = _cursor.getLong(_cursorIndexOfSunrise);
        _item.setSunrise(_tmpSunrise);
        final long _tmpSunset;
        _tmpSunset = _cursor.getLong(_cursorIndexOfSunset);
        _item.setSunset(_tmpSunset);
        final long _tmpDt;
        _tmpDt = _cursor.getLong(_cursorIndexOfDt);
        _item.setDt(_tmpDt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<ListWeatherEntry>> getMidDayForecast(
      final Date tomorrowMidnightNormalizedUtc, final long offset, final long hourInMillis) {
    final String _sql = "SELECT id, date, weatherId, iconCodeOWM, temperature FROM weather WHERE date > ? AND (date + ?) % (24 * ?) BETWEEN (11 * ? +1) AND 14 * ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(tomorrowMidnightNormalizedUtc);
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
    return __db.getInvalidationTracker().createLiveData(new String[]{"weather"}, false, new Callable<List<ListWeatherEntry>>() {
      @Override
      public List<ListWeatherEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeatherId = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherId");
          final int _cursorIndexOfIconCodeOWM = CursorUtil.getColumnIndexOrThrow(_cursor, "iconCodeOWM");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final List<ListWeatherEntry> _result = new ArrayList<ListWeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ListWeatherEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final int _tmpWeatherId;
            _tmpWeatherId = _cursor.getInt(_cursorIndexOfWeatherId);
            final String _tmpIconCodeOWM;
            _tmpIconCodeOWM = _cursor.getString(_cursorIndexOfIconCodeOWM);
            final double _tmpTemperature;
            _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
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
    });
  }
}
