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
package com.example.android.sunshine.data.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.android.sunshine.AppExecutors;
import com.example.android.sunshine.BuildConfig;
import com.example.android.sunshine.data.database.WeatherEntry;
import com.example.android.sunshine.utilities.ForegroundListener;
import com.example.android.sunshine.utilities.Utils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Provides an API for doing all operations with the server data
 */
public class NetworkDataSource {
    // The number of days we want our API to return, set to 14 days or two weeks
    public static final int NUM_DAYS = 5;
    public static final int NUM_MIN_DATA_COUNTS = (NUM_DAYS - 1) * (24 / 3);
    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    // Interval at which to sync with the weather. Use TimeUnit for convenience, rather than
    // writing out a bunch of multiplication ourselves and risk making a silly mistake.
    private static final int SYNC_INTERVAL_HOURS = 6;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    //    private static final int SYNC_INTERVAL_SECONDS = 5 * 60;
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String SUNSHINE_SYNC_TAG = "sunshine-sync";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkDataSource sInstance;
    private final Context context;

    private final AppExecutors mExecutors;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<WeatherEntry[]> mDownloadedWeatherForecasts;
    private final MutableLiveData<WeatherEntry[]> mDownloadedCurrentWeather;

    private NetworkDataSource(Context context, AppExecutors executors) {
        this.context = context;
        mExecutors = executors;
        mDownloadedWeatherForecasts = new MutableLiveData<WeatherEntry[]>();
        mDownloadedCurrentWeather = new MutableLiveData<WeatherEntry[]>();
    }

    public LiveData<WeatherEntry[]> getCurrentWeatherForecasts() {
        return mDownloadedWeatherForecasts;
    }

    public LiveData<WeatherEntry[]> getCurrentWeather() {
        return mDownloadedCurrentWeather;
    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    /**
     * Starts an intent service to fetch the weather.
     */
    public void startFetchWeatherService() {
        Intent intentToFetch = new Intent(context, SyncIntentService.class);
        context.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }

    public void startFetchCurrentWeatherService() {
        Intent intentToFetch = new Intent(context, SyncIntentServiceCW.class);
        context.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created - current weather");
    }

    /**
     * Schedules a repeating job service which fetches the weather.
     */
    public void scheduleRecurringFetchWeatherSync() {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        // Create the Job to periodically sync Sunshine
        Job syncSunshineJob = dispatcher.newJobBuilder()
                /* The Service that will be used to sync Sunshine's data */
                .setService(MyJobService.class)
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
                 * We want Sunshine's weather data to stay up to date, so we tell this Job to recur.
                 */
                .setRecurring(true)
                /*
                 * We want the weather data to be synced every 3 to 4 hours. The first argument for
                 * Trigger's static executionWindow method is the start of the time frame when the
                 * sync should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncSunshineJob);
        Log.d(LOG_TAG, "Job scheduled");
    }

    /**
     * Gets the newest weather
     */
    void fetchWeather() {
        Log.d(LOG_TAG, "Fetch weather started");
        mExecutors.networkIO().execute(() -> {
            try {

                // The getUrl method will return the URL that we need to get the forecast JSON for the
                // weather. It will decide whether to create a URL based off of the latitude and
                // longitude or off of a simple location as a String.

                URL weatherRequestUrl = NetworkUtils.getUrl(context);

                // Use the URL to retrieve the JSON
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                // Parse the JSON into a list of weather forecasts
                WeatherResponse response = new WeatherJsonParser().parse(jsonWeatherResponse);
                Log.d(LOG_TAG, "JSON Parsing finished");


                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the
                // RepositoryWeather.
                if (response != null && response.getWeatherForecast().length != 0) {
                    Log.d(LOG_TAG, "JSON not null and has " + response.getWeatherForecast().length
                            + " values");
                    Log.d(LOG_TAG, String.format("First value is %1.0f, date %s  icon %s",
                            response.getWeatherForecast()[0].getTemp(),
                            response.getWeatherForecast()[0].getDate(),
                            response.getWeatherForecast()[0].getIcon()));

                    mDownloadedWeatherForecasts.postValue(response.getWeatherForecast());
                    // Will eventually do something with the downloaded data

                    if (BuildConfig.DEBUG) {
                        SharedPreferences prefs = context.getSharedPreferences("_", Context.MODE_PRIVATE);
                        String txt = prefs.getString("txt", "");
                        long now = System.currentTimeMillis();
                        String date = new SimpleDateFormat("HH:mm  dd MMM", Locale.getDefault()).format(now);
                        txt += date + "\n";
                        prefs.edit().putString("txt", txt).apply();

                    }

                    boolean notificationsEnabled = Utils.INSTANCE.areNotificationsEnabled(context);
                    long timeSinceLastNotification = Utils.INSTANCE.getEllapsedTimeSinceLastNotification(context);
                    boolean oneDayPassedSinceLastNotification = false;

                    if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS) {
                        oneDayPassedSinceLastNotification = true;
                    }

                    if (notificationsEnabled && oneDayPassedSinceLastNotification) {
                        WeatherEntry entry = response.getWeatherForecast()[0];

                        if (!ForegroundListener.Companion.isForeground()) {
                            Utils.INSTANCE.notifyUserOfNewWeather(context, entry);
                        }

                    }


                }
            } catch (Exception e) {
                // Server probably invalid
                e.printStackTrace();
            }
        });
    }

    void fetchCurrentWeather() {
        Log.d(LOG_TAG, "Fetch weather NOW started");
        mExecutors.networkIO().execute(() -> {
            try {

                // The getUrl method will return the URL that we need to get the forecast JSON for the
                // weather. It will decide whether to create a URL based off of the latitude and
                // longitude or off of a simple location as a String.

//                URL weatherRequestUrl = NetworkUtils.getUrl_();                                           // for test server
                URL weatherRequestUrl = NetworkUtils.getUrlCurrentWeather(context);

                // Use the URL to retrieve the JSON
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

                // Parse the JSON into a list of weather forecasts
//                WeatherResponse response = new WeatherJsonParser().parse_(jsonWeatherResponse);       // for test server
                WeatherResponse response = new WeatherJsonParser().parseCurrentWeather(jsonWeatherResponse);
                Log.d(LOG_TAG, "JSON Parsing finished Weather Now");


                // As long as there are weather forecasts, update the LiveData storing the most recent
                // weather forecasts. This will trigger observers of that LiveData, such as the
                // RepositoryWeather.
                if (response != null && response.getWeatherForecast().length != 0) {
                    Log.d(LOG_TAG, "JSON not null and has " + response.getWeatherForecast().length
                            + " values");
                    Log.d(LOG_TAG, String.format("Current Weather First value is %1.0f,  date %s  icon %s,  isCurrentW %d",
                            response.getWeatherForecast()[0].getTemp(),
                            response.getWeatherForecast()[0].getDate(),
                            response.getWeatherForecast()[0].getIcon(),
                            response.getWeatherForecast()[0].isCurrentWeather()));

                    WeatherEntry[] entries = response.getWeatherForecast();

                    mDownloadedCurrentWeather.postValue(entries);
                    // Will eventually do something with the downloaded data
                }
            } catch (Exception e) {
                // Server probably invalid
                e.printStackTrace();
            }
        });
    }


}