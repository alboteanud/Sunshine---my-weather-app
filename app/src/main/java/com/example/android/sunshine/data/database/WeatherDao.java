package com.example.android.sunshine.data.database;

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
    @Query("SELECT id, weatherIconId, date, `temp`, icon FROM weather WHERE date >= :date LIMIT 6")
    LiveData<List<ListWeatherEntry>> getCurrentWeatherForecasts(Date date);

    /**
     * Selects all ids entries after a give _date, inclusive. This is for easily seeing
     * what entries are in the database without pulling all of the data.
     *
     * @param date The _date to select after (inclusive)
     * @return Number of future weather forecasts stored in the database
     */
    @Query("SELECT * FROM weather WHERE date = :date")
    int countAllFutureWeather(Date date);

    //    Gets the weather for a single day
    @Query("SELECT * FROM weather WHERE date = :date")
    LiveData<WeatherEntry> getWeatherByDate(Date date);

    @Query("SELECT date FROM weather WHERE date > :date AND isCurrentWeather = 1")
    Date getLastUpdatedDateCW(Date date);

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
     * +     * Deletes any weather data older than the given day
     * +     *
     * +     * @param _date The _date to delete all prior weather from (exclusive)
     * +
     */
    @Query("DELETE FROM weather WHERE date < :date")
    void deleteOldWeather(Date date);

//    @Query("SELECT id, weatherIconId, date, `temp`, icon, humidity, pressure, wind, degrees, isCurrentWeather FROM weather WHERE date >= :date " +
    @Query("SELECT * FROM weather WHERE date >= :date " +
            "ORDER BY isCurrentWeather DESC " +
            "LIMIT 1")
    LiveData<WeatherEntry>getCurrentWeather(Date date);

}
