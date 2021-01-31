package com.craiovadata.android.sunshine.data.database

import java.util.Date

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.craiovadata.android.sunshine.ui.models.ListWeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.WebcamEntry

@Dao
interface
WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(vararg weather: WeatherEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsertWebcams(vararg webcams: WebcamEntry)

    @Query("SELECT * FROM weather")
    fun getAllWeatherEntries(): LiveData<List<WeatherEntry>>

    @Query("SELECT * FROM weather LIMIT 1")
    fun getOneRandomWeatherEntry(): WeatherEntry?

//    @Query("SELECT id, weatherId, date, temperature, iconCodeOWM FROM weather WHERE date >= :date ORDER BY date ASC LIMIT 5")
    @Query("SELECT date, weatherId, temperature, iconCodeOWM FROM weather WHERE date >= :date ORDER BY date ASC LIMIT 5")
    fun getCurrentForecast(date: Date): LiveData<List<ListWeatherEntry>>

    @Query("SELECT COUNT(*) FROM weather WHERE date > :date")
    fun countAllFutureWeatherEntries(date: Date): Int

    @Query("SELECT COUNT(*) FROM weather WHERE date >= :recently AND isCurrentWeather = 1")
    fun countCurrentWeather(recently: Date): Int

    @Query("DELETE FROM weather WHERE date < :recently")
    fun deleteOldWeather(recently: Date)

    @Query("DELETE FROM webcams WHERE updateDate < :mDate")
    fun deleteOldWebcams(mDate: Date)

    @Query("SELECT COUNT(*) FROM webcams ")
    fun countAllWebcamEntries(): Int

    @Query("SELECT * FROM weather WHERE date  >= :recentlyDate ORDER BY isCurrentWeather DESC, date ASC LIMIT :limit")
    fun getCurrentWeather(recentlyDate: Date, limit: Int): LiveData<List<WeatherEntry>>

    @Query("SELECT * FROM weather WHERE date  >= :recentlyDate ORDER BY isCurrentWeather DESC, date ASC LIMIT 1")
    fun getCurrentWeatherList(recentlyDate: Date): List<WeatherEntry>

//    @Query("SELECT id, date, weatherId, iconCodeOWM, temperature FROM weather WHERE date > :tomorrowMidnightNormalizedUtc AND (date + :offset) % (24 * :hourInMillis) BETWEEN (11 * :hourInMillis +1) AND 14 * :hourInMillis")
    @Query("SELECT date, weatherId, iconCodeOWM, temperature FROM weather WHERE date > :tomorrowMidnightNormalizedUtc AND (date + :offset) % (24 * :hourInMillis) BETWEEN (11 * :hourInMillis +1) AND 14 * :hourInMillis")
    fun getMidDayForecast(
        tomorrowMidnightNormalizedUtc: Date,
        offset: Long,
        hourInMillis: Long
    ): LiveData<List<ListWeatherEntry>>

    @Query("SELECT * FROM webcams LIMIT 1")
    fun getLatestWebcam(): List<WebcamEntry>

    @Query("SELECT * FROM webcams")
    fun getAllWebcamEntries(): LiveData<List<WebcamEntry>>
}
