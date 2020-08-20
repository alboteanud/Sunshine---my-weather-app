package com.craiovadata.android.sunshine.data.database

import android.text.format.DateUtils
import android.text.format.DateUtils.DAY_IN_MILLIS
import android.text.format.DateUtils.HOUR_IN_MILLIS
import androidx.lifecycle.LiveData
import com.craiovadata.android.sunshine.utilities.AppExecutors
import com.craiovadata.android.sunshine.data.network.NetworkDataSource
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.ui.models.ListWeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.WebcamEntry
import com.craiovadata.android.sunshine.utilities.LogUtils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Handles data operations in Sunshine. Acts as a mediator between [NetworkDataSource]
 * and [WeatherDao]
 */
class Repository private constructor(
    private val mWeatherDao: WeatherDao,
    private val mNetworkDataSource: NetworkDataSource,
    private val mExecutors: AppExecutors
) {
    private var initializedForecast = false
    private var initializedCurrentWeather = false
    private var initializedWebcams = false

    init {

        GlobalScope.launch(Dispatchers.Main) {

            mNetworkDataSource.forecasts.observeForever { newForecastsFromNetwork ->
                mExecutors.diskIO().execute {
                    // Deletes old historical data
                    deleteOldWeatherData()
                    // Insert our new weather data into Sunshine's database
                    mWeatherDao.bulkInsert(*newForecastsFromNetwork)
                    log( "Old weather deleted. New values inserted.")
                }
            }


            mNetworkDataSource.currentWeather.observeForever { newDataFromNetwork ->
                mExecutors.diskIO().execute {
                    mWeatherDao.bulkInsert(*newDataFromNetwork)
                }
            }

            mNetworkDataSource.webcams.observeForever { newWebcamsFromNetwork ->
                mExecutors.diskIO().execute {
                    // Deletes old historical data
                    deleteOldWebcamsData()
                    // Insert our new weather data into Sunshine's database
                    mWeatherDao.bulkInsertWebcams(*newWebcamsFromNetwork)
                    log( "Old webcams deleted. New values inserted.")
                }
            }

        }

    }

    @Synchronized
    private fun refreshDataCurrentWeather() {
        mExecutors.diskIO().execute {
            if (isFetchCurrentWeatherNeeded)
                mNetworkDataSource.fetchCurrentWeather()
        }
    }

    val dayWeatherEntries: LiveData<List<ListWeatherEntry>>
        get() {
            initializeForecastData()
            val offset = CityData.getCityOffset()

            val daysSinceEpoch = TimeUnit.MILLISECONDS.toDays(currentTimeMillis())
            val tomorrowMidnightNormalizedUtc = (daysSinceEpoch + 1) * DAY_IN_MILLIS

            return mWeatherDao.getMidDayForecast(
                Date(tomorrowMidnightNormalizedUtc),
                offset,
                HOUR_IN_MILLIS
            )
        }

    val webcamsEntries: LiveData<List<WebcamEntry>>
        get() {
            initializeWebcamData(null)
            return mWeatherDao.getAllWebcamEntries()
        }

    val currentWeatherList: List<WeatherEntry>
        get() {
            initDataCurrentWeather()
            val recentlyMills = currentTimeMillis() - DateUtils.MINUTE_IN_MILLIS * delay
            val recentDate = Date(recentlyMills)
            return mWeatherDao.getCurrentWeatherList(recentDate)
        }

    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private val isFetchForecastNeeded: Boolean
        get() {
            val now = Date(currentTimeMillis())
            val count = mWeatherDao.countAllFutureWeatherEntries(now)
            return count < NetworkDataSource.NUM_MIN_DATA_COUNTS
        }

    private val isFetchCurrentWeatherNeeded: Boolean
        get() {
            val dateRecently = Date(currentTimeMillis() - DateUtils.MINUTE_IN_MILLIS * delay)
            val count = mWeatherDao.countCurrentWeather(dateRecently)
            val isFetchNeededCW = count < 1
            log("isFetchNeededCW: $isFetchNeededCW")
            return isFetchNeededCW
        }

    private val isFetchWebcamsNeeded: Boolean
        get() {
            val webcams = mWeatherDao.getLatestWebcam()

            if (webcams.isNullOrEmpty()) return true
            // check if data webcams is older than one week
            val oneWeekAgo = Date(currentTimeMillis() - 7 * DAY_IN_MILLIS )
            val isOldWebcamsData = webcams[0].updateDate < oneWeekAgo
            return isOldWebcamsData
        }

    /** Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    @Synchronized
    fun initializeForecastData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (initializedForecast) return
        initializedForecast = true

        mNetworkDataSource.scheduleFetchWeather()

//            // java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
        mExecutors.diskIO().execute {
            if (!isFetchForecastNeeded) return@execute
            mNetworkDataSource.fetchWeather { firstWeatherEntry ->
                if (firstWeatherEntry != null){
                    initializeWebcamData(firstWeatherEntry)
                }

            }
        }
    }

    private fun initializeWebcamData(weatherEntry: WeatherEntry?) {

        mExecutors.diskIO().execute {

            val weather = weatherEntry ?: mWeatherDao.getOneRandomWeatherEntry() ?: return@execute

            if (initializedWebcams) return@execute
            initializedWebcams = true

            mNetworkDataSource.scheduleFetchWebcams(weather)
            if (!isFetchWebcamsNeeded) return@execute
            mNetworkDataSource.fetchWebcams (weather.lat, weather.lon){success ->

            }
        }
    }

    @Synchronized
    private fun initDataCurrentWeather() {
        if (initializedCurrentWeather) return
        initializedCurrentWeather = true

        mExecutors.diskIO().execute {
            if (isFetchCurrentWeatherNeeded)
                mNetworkDataSource.fetchCurrentWeather()
        }
    }

    private fun deleteOldWeatherData() {
        //        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        val oldTime = currentTimeMillis() - HOUR_IN_MILLIS
        val date = Date(oldTime)
        mWeatherDao.deleteOldWeather(date)
    }
 private fun deleteOldWebcamsData() {
        mWeatherDao.deleteOldWebcams(Date())
    }

    fun getCurrentWeather(timestamp: Long): LiveData<List<WeatherEntry>>? {
        refreshDataCurrentWeather()
        val recentlyMills = timestamp - DateUtils.MINUTE_IN_MILLIS * delay
        val recentDate = Date(recentlyMills)
        val limitCountData = if (inTestMode) 3 else 1
        log("get currentWeather more recent than $recentDate")
        return mWeatherDao.getCurrentWeather(recentDate, limitCountData)

    }

    fun getWeatherNextHours(timestamp: Long): LiveData<List<ListWeatherEntry>> {
        val date = Date(timestamp - 1)
        return mWeatherDao.getCurrentForecast(date)

    }

    companion object {

        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: Repository? = null

        @Synchronized
        fun getInstance(
            weatherDao: WeatherDao, networkDataSource: NetworkDataSource,
            executors: AppExecutors
        ): Repository {
            log("Getting the repository")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance =
                        Repository(
                            weatherDao, networkDataSource,
                            executors
                        )
                    log("Made new repository")
                }
            }
            return sInstance!!
        }

        private const val delay = 10
    }
}