package com.craiovadata.android.sunshine.data.database;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


/**
 * {@link Dao} which provides an api for all data operations
 */
@Dao
public interface WeatherDao {

    /**
     * +     * Selects all {@link ListWeatherEntry} entries after a give _date, inclusive. The LiveData will
     * +     * be kept in sync with the database, so that it will automatically notify observers when the
     * +     * values in the table change.
     * +     *
     * +     * @param _date A {@link Date} from which to select all future weather
     * +     * @return {@link LiveData} list of all {@link ListWeatherEntry} objects after _date
     * +
     */
    @Query("SELECT id, weatherId, date, temperature, iconCodeOWM FROM weather WHERE date >= :date " +
            "ORDER BY date ASC " +
            "LIMIT 5 ")
    LiveData<List<ListWeatherEntry>> getCurrentForecast(Date date);

    /**
     * Selects all ids entries after a give _date, inclusive. This is for easily seeing
     * what entries are in the database without pulling all of the data.
     *
     * @param date The _date to select after (inclusive)
     * @return Number of future weather forecasts stored in the database
     */
    @Query("SELECT COUNT(*) FROM weather WHERE date > :date")
    int countAllFutureWeatherEntries(Date date);

    @Query("SELECT COUNT(*) FROM weather WHERE date >= :recently AND isCurrentWeather = 1")
    int countCurrentWeather(Date recently);

    /**
     * Inserts a list of {@link WeatherEntry} into the weather table. If there is a conflicting _id
     * or _date the weather entry uses the {@link OnConflictStrategy} of replacing the weather
     * forecast. The required uniqueness of these values is defined in the {@link WeatherEntry}.
     *
     * @param weather A list of weather forecasts to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(WeatherEntry... weather);

    /**
     * Deletes any weather data older than the given day
     *
     * @param recently The _date to delete all prior weather from (exclusive)
     */
    @Query("DELETE FROM weather WHERE date < :recently")
    void deleteOldWeather(Date recently);

    @Query("SELECT * FROM weather WHERE date  >= :recentlyDate " +
            "ORDER BY isCurrentWeather DESC, ABS(:nowDate - date) ASC " +
            "LIMIT 1")
    LiveData<List<WeatherEntry>> getCurrentWeather_old(Date nowDate, Date recentlyDate);

    @Query("SELECT * FROM weather WHERE date  >= :recentlyDate " +
            "ORDER BY isCurrentWeather DESC, date ASC " +
            "LIMIT 1")
    LiveData<List<WeatherEntry>> getCurrentWeather(Date recentlyDate);

    @Query("SELECT * FROM weather WHERE date  >= :recentlyDate " +
            "ORDER BY isCurrentWeather DESC, ABS(:nowDate - date) ASC " +
            "LIMIT 1")
    List<WeatherEntry> getCurrentWeatherList(Date nowDate, Date recentlyDate);


    @Query("SELECT * FROM weather")
    LiveData<List<WeatherEntry>> getAllEntries();


    @Query("SELECT id, date, weatherId, iconCodeOWM, temperature FROM weather WHERE date > :tomorrowMidnightNormalizedUtc " +
            "AND (date + :offset) % (24 * :hourInMillis) " +
            "BETWEEN (11 * :hourInMillis +1) AND 14 * :hourInMillis")
    LiveData<List<ListWeatherEntry>> getMidDayForecast(Date tomorrowMidnightNormalizedUtc, long offset, long hourInMillis);


}
