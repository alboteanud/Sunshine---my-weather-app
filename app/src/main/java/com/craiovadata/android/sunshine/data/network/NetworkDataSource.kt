/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version c2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craiovadata.android.sunshine.data.network

import android.content.Context
import android.content.Intent
import android.util.Log

import com.craiovadata.android.sunshine.utilities.AppExecutors
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.utilities.NotifUtils
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.Trigger

import java.util.concurrent.TimeUnit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//import com.craiovadata.android.sunshine.utilities.AppExecutors;

/**
 * Provides an API for doing all operations with the server data
 */
class NetworkDataSource private constructor(
    private val context: Context,
    private val mExecutors: AppExecutors
) {
    // LiveData storing the latest downloaded weather forecasts
    private val mDownloadedWeatherForecasts: MutableLiveData<Array<WeatherEntry>> = MutableLiveData()
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

    fun startFetchCurrentWeatherService() {
        val intentToFetch = Intent(context, SyncIntentServiceCW::class.java)
        try {
            context.startService(intentToFetch)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.i(LOG_TAG, "Service created - getting current weather")
    }

    /**
     * Schedules a repeating job service which fetches the weather.
     */
    fun scheduleRecurringFetchWeatherSync() {
        val driver = GooglePlayDriver(context)
        val dispatcher = FirebaseJobDispatcher(driver)

        // Create the Job to periodically sync Sunshine
        val syncSunshineJob = dispatcher.newJobBuilder()
            /* The Service that will be used to sync Sunshine's data */
            .setService(MyJobService::class.java)
            /* Set the UNIQUE tag used to identify this Job */
            .setTag(SUNSHINE_SYNC_TAG)
            /*
                 * Network constraints on which this Job should run. We choose to run on any
                 * network, but you can also choose to run only on un-metered networks or when the
                 * device is charging. It might be a good idea to include a preference for this,
                 * as some users may not want to download any data on their mobile plan. ($$$)
                 */
            .setConstraints(Constraint.ON_ANY_NETWORK)
            /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
            .setLifetime(Lifetime.FOREVER)
            /*
                 * We want Sunshine's weather data to stay up to _date, so we tell this Job to recur.
                 */
            .setRecurring(true)
            /*
                 * We want the weather data to be synced every 3 to 4 hours. The first argument for
                 * Trigger's static executionWindow method is the start of the time frame when the
                 * sync should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
            .setTrigger(
                Trigger.executionWindow(
                    SYNC_INTERVAL_SECONDS,
                    SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                )
            )
            /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
            .setReplaceCurrent(true)
            /* Once the Job is ready, call the builder's build method to return the Job */
            .build()

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncSunshineJob)
        Log.d(LOG_TAG, "Job scheduled")
    }

    /**
     * Gets the newest weather
     */
    fun fetchWeather() {
        Log.d(LOG_TAG, "Fetch weather days started")
        mExecutors.networkIO().execute {
            try {

                // The getUrl method will return the URL that we need to get the forecast JSON for the
                // weather. It will decide whether to create a URL based off of the latitude and
                // longitude or off of a simple location as a String.

                val weatherRequestUrl = NetworkUtils.getUrl(context)

                // Use the URL to retrieve the JSON
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                // Parse the JSON into a list of weather forecasts
                val response = WeatherJsonParser().parseForecastWeather(jsonWeatherResponse)
                Log.d(LOG_TAG, "weather days -JSON Parsing finished")


                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the
                // RepositoryWeather.
                if (response != null && response.weatherForecast.size != 0) {
                    mDownloadedWeatherForecasts.postValue(response.weatherForecast)
                    NotifUtils.notifyIfNeeded(context, response.weatherForecast[0])

                    Log.d(
                        LOG_TAG,
                        "weather days - JSON not null and has " + response.weatherForecast.size + " values"
                    )
                    //                    LogUtils.logResponse(LOG_TAG, response.getWeatherForecast()[0]);
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

                // The getUrl method will return the URL that we need to get the forecast JSON for the
                // weather. It will decide whether to create a URL based off of the latitude and
                // longitude or off of a simple location as a String.

                //                URL weatherRequestUrl = NetworkUtils.getUrl_();                                           // for test server
                val weatherRequestUrl = NetworkUtils.getUrlCurrentWeather(context)

                // Use the URL to retrieve the JSON
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl)

                // Parse the JSON into a list of weather forecasts
                //                WeatherResponse response = new WeatherJsonParser().parse_(jsonWeatherResponse);       // for test server
                val response = WeatherJsonParser().parseCurrentWeather(jsonWeatherResponse)
                Log.e(LOG_TAG, "JSON Parsing finished Current Weather")


                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the
                // RepositoryWeather.
                if (response != null && response.weatherForecast.size != 0) {
                    val entries = response.weatherForecast
                    mDownloadedCurrentWeather.postValue(entries)
                    // Will eventually do something with the downloaded data

                    if (BuildConfig.DEBUG) {
                        val entry = entries[0]
                        Log.i(
                            LOG_TAG, String.format(
                                "parsed CurrentWeather - temp %1.0f  %s ",
                                entry.temperature,
                                entry.date
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                // Server probably invalid
                e.printStackTrace()
            }
        }
    }

    companion object {

        private val LOG_TAG = NetworkDataSource::class.java.simpleName

        // Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
        // writing out a bunch of multiplication ourselves and risk making a silly mistake.
        private val SYNC_INTERVAL_HOURS = 8
        val NUM_MIN_DATA_COUNTS = (5 - 1) * 8
        private val SYNC_INTERVAL_SECONDS =
            TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS.toLong()).toInt()
        //    private static final int SYNC_INTERVAL_SECONDS = 5 * 60;
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3
        private val SUNSHINE_SYNC_TAG = "sunshine-sync"

        // For Singleton instantiation
        private val LOCK = Any()
        private var sInstance: NetworkDataSource? = null

        /**
         * Get the singleton for this class
         */
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
    }


}