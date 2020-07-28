package com.craiovadata.android.sunshine.data.network

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import androidx.work.*
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.ui.main.MainActivity
import com.craiovadata.android.sunshine.ui.main.MainActivity.Companion.PREF_SYNC_KEY
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.utilities.AppExecutors
import com.craiovadata.android.sunshine.utilities.NotifUtils
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Provides an API for doing all operations with the server data
 */
class NetworkDataSource private constructor(
    private val context: Context,
    private val mExecutors: AppExecutors
) {

    private val mWorkManager: WorkManager = WorkManager.getInstance(context)

    // LiveData storing the latest downloaded weather forecasts
    private val mDownloadedWeatherForecasts: MutableLiveData<Array<WeatherEntry>> =
        MutableLiveData()
    private val mDownloadedCurrentWeather: MutableLiveData<Array<WeatherEntry>> = MutableLiveData()

    val forecasts: LiveData<Array<WeatherEntry>>
        get() = mDownloadedWeatherForecasts

    val currentWeather: LiveData<Array<WeatherEntry>>
        get() = mDownloadedCurrentWeather

    /**
     * Starts an intent service to fetch the weather.
     */
    fun startFetchWeatherService() {
        val intentToFetch = Intent(context, SyncIntentService::class.java)
        context.startService(intentToFetch)
        Log.d(LOG_TAG, "Service created - fetching weather by days")
    }

    fun startFetchWeatherServiceTest(citiesIndex: Int) {
        val intentToFetch = Intent(context, SyncIntentServiceTest::class.java)
        intentToFetch.putExtra("citiesIndex", citiesIndex)
        context.startService(intentToFetch)
        Log.d(LOG_TAG, "Service created - fetching weather by days")
    }

    fun startFetchCurrentWeatherService() {
        val intentToFetch = Intent(context, SyncIntentServiceCW::class.java)
        try {
            context.startService(intentToFetch)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.i(LOG_TAG, "Service created - getting current weather")
    }

    fun scheduleFetchWeather() {
//        val input = workDataOf("some_key" to "some_val")
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresBatteryNotLow(true)
//            setRequiresDeviceIdle(true)     // not working with BackoffPolicy
        }.build()

        val repeatInterval: Long = if (inTestMode) 1 else 6
        val request: PeriodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, repeatInterval, TimeUnit.HOURS, 1, TimeUnit.HOURS)
//                .setInputData(input)
            .setConstraints(constraints)
            .setInitialDelay(repeatInterval, TimeUnit.HOURS)
//                .addTag(TAG_WORK_NAME)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 1, TimeUnit.HOURS)
            .build()
        mWorkManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    fun fetchWeather() {
        Log.d(LOG_TAG, "Fetch weather days started")
        addTestText(context, "feW")
        mExecutors.networkIO().execute {
            try {
                val weatherRequestUrl = NetworkUtils.getUrl(context)

                // Use the URL to retrieve the JSON
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                // Parse the JSON into a list of weather forecasts
                val response = WeatherJsonParser().parseForecastWeather(jsonWeatherResponse)

                Log.d(LOG_TAG, "weather JSON has ${response.weatherForecast.size} values")
                addTestText(context, "sy${response.weatherForecast.size}")

                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the Repo

                if (!response.weatherForecast.isNullOrEmpty()) {
                    val entries = response.weatherForecast
                    mDownloadedWeatherForecasts.postValue(entries)
                    NotifUtils.notifyIfNeeded(context, entries[0])

                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
                addTestText(context, "fetchErr:$e")
            }
        }
    }

    fun fetchWeatherForMultipleCitiesTest(
        context: Context,
        cityIds: List<Int>
    ) {
        Log.d(LOG_TAG, "Fetch weather days started")
        mExecutors.networkIO().execute {
            try {
                val descriptions = mutableMapOf<Int, String>()
                cityIds.forEach { id ->
                    val weatherRequestUrl =
                        NetworkUtils.getUrl2(
                            context,
                            id,
                            MainActivity.languageParamMultipleCitiesTest
                        ) ?: return@execute

                    // Use the URL to retrieve the JSON
                    val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                    // Parse the JSON into a list of weather forecasts
                    val response = WeatherJsonParser().parseForecastWeather2(jsonWeatherResponse)
                    Log.d(LOG_TAG, "parsing finished. Size: ${response.weatherForecast.size} ")
                    response.weatherForecast.forEach { entry ->

                        if (!descriptions.containsKey(entry.id)) {
                            descriptions[entry.id] = entry.description
                            if (inTestMode) {
                                val descriptionStringName = "condition_" + entry.id.toString()
                                val res: Resources = context.resources
                                val isTranslated = res.getString(
                                    res.getIdentifier(
                                        descriptionStringName, "string", context.packageName
                                    )
                                ) == entry.description

                                if (!isTranslated)
                                    Log.e(
                                        "NEW description",
                                        entry.id.toString() + "  " + entry.description
                                    )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
            }
        }
    }

    fun fetchCurrentWeather() {
        Log.i(LOG_TAG, "Fetch current weather started")
        mExecutors.networkIO().execute {
            try {
                //                URL weatherRequestUrl = NetworkUtils.getUrl_();                                           // for test server
                val weatherRequestUrl = NetworkUtils.getUrlCurrentWeather(context) ?: return@execute
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                val response = WeatherJsonParser().parseCurrentWeather(jsonWeatherResponse)
                Log.e(LOG_TAG, "JSON Parsing finished Current Weather: ${response.weatherForecast[0].degrees}")

                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the RepositoryWeather.
                if (response.weatherForecast.isNotEmpty()) {
                    val entries = response.weatherForecast
                    mDownloadedCurrentWeather.postValue(entries)
                    // Will eventually do something with the downloaded data
                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
            }
        }
    }

    companion object {
        private val LOG_TAG = NetworkDataSource::class.java.simpleName
        val NUM_MIN_DATA_COUNTS = if (inTestMode) 10 else 39

        const val WORK_NAME = "my-work-name-v3"

        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: NetworkDataSource? = null

        // Get the singleton for this class
        fun getInstance(context: Context, executors: AppExecutors): NetworkDataSource {
            Log.d(LOG_TAG, "Getting the network data source")
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = NetworkDataSource(context.applicationContext, executors)
                    Log.d(LOG_TAG, "Made new network data source")
                }
            }
            return sInstance!!
        }


        fun addTestText(context: Context, text: String) {
            if (!inTestMode) return
            val pref = PreferenceManager.getDefaultSharedPreferences(context)
            var savedTxt = pref.getString(PREF_SYNC_KEY, "")
            val format = SimpleDateFormat("HH.mm")
            savedTxt += " $text" + "_" + format.format(currentTimeMillis())
            pref.edit().putString(PREF_SYNC_KEY, savedTxt).apply()
        }

    }

}