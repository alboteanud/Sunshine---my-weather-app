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

package com.craiovadata.android.sunshine.data;

import android.text.format.DateUtils;
import android.util.Log;

import com.craiovadata.android.sunshine.AppExecutors;
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry;
import com.craiovadata.android.sunshine.data.database.WeatherDao;
import com.craiovadata.android.sunshine.data.database.WeatherEntry;
import com.craiovadata.android.sunshine.data.network.NetworkDataSource;
import com.craiovadata.android.sunshine.utilities.Utils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.HOUR_IN_MILLIS;

//import com.craiovadata.android.sunshine.AppExecutors;

/**
 * Handles data operations in Sunshine. Acts as a mediator between {@link NetworkDataSource}
 * and {@link WeatherDao}
 */
public class RepositoryWeather {
    private static final String LOG_TAG = RepositoryWeather.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static RepositoryWeather sInstance;
    private final WeatherDao mWeatherDao;
    private final NetworkDataSource mNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;
    private boolean mInitializedCW = false;

    private RepositoryWeather(WeatherDao weatherDao,
                              NetworkDataSource networkDataSource,
                              AppExecutors executors) {
        mWeatherDao = weatherDao;
        mNetworkDataSource = networkDataSource;
        mExecutors = executors;

        LiveData<WeatherEntry[]> networkData = mNetworkDataSource.getCurrentWeatherForecasts();
        networkData.observeForever(newForecastsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(LOG_TAG, "Old weather deleted");
                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(newForecastsFromNetwork);
                Log.d(LOG_TAG, "New values inserted.");

            });
        });

        LiveData<WeatherEntry[]> networkDataCW = mNetworkDataSource.getCurrentWeather();
        networkDataCW.observeForever(newDataFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
//                deleteOldData();
                mWeatherDao.bulkInsert(newDataFromNetwork);

            });
        });
    }

    public synchronized static RepositoryWeather getInstance(
            WeatherDao weatherDao, NetworkDataSource networkDataSource,
           AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RepositoryWeather(weatherDao, networkDataSource,
                        executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    public synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        // This method call triggers Sunshine to create its task to synchronize weather data
        // periodically.
        mNetworkDataSource.scheduleRecurringFetchWeatherSync();

        mExecutors.diskIO().execute(() -> {
            Log.d(LOG_TAG, "execute initData");
            if (isFetchNeeded()) {
                startFetchWeatherService();
            }
        });

    }

    public void initializeDataCW() {
        if (mInitializedCW) return;
        mInitializedCW = true;

        mExecutors.diskIO().execute(() -> {
            Log.d(LOG_TAG, "execute initDataCurrentWeather");
            if (isFetchNeededCW()) {
                startFetchCWeatherService();
            }
        });
    }

    public void resetInitializedCW() {
        mInitializedCW = false;
    }

    /**
     * Database related operations
     **/
    public LiveData<List<ListWeatherEntry>> getNextHoursWeather() {
        initializeData();
        long utcNowMillis = System.currentTimeMillis();
        Date date = new Date(utcNowMillis);
        return mWeatherDao.getCurrentForecast(date);
    }

    public LiveData<List<WeatherEntry>> getCurrentWeather() {
        initializeDataCW();

        long nowMills = System.currentTimeMillis();
        Date nowDate = new Date(nowMills);

        long recentlyMills = nowMills - DateUtils.MINUTE_IN_MILLIS * 30;
        Date recentDate = new Date(recentlyMills);

        return mWeatherDao.getCurrentWeather(nowDate, recentDate);
    }

 public List<WeatherEntry> getCurrentWeatherList() {
//        initializeDataCW();

        long nowMills = System.currentTimeMillis();
        Date nowDate = new Date(nowMills);

        long recentlyMills = nowMills - DateUtils.MINUTE_IN_MILLIS * 30;
        Date recentDate = new Date(recentlyMills);

        return mWeatherDao.getCurrentWeatherList(nowDate, recentDate);
    }

    private void deleteOldData() {
//        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        long oldTime = System.currentTimeMillis() - HOUR_IN_MILLIS;
        Date date = new Date(oldTime);
        mWeatherDao.deleteOldWeather(date);
    }

    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded() {
//        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        Date now = new Date(System.currentTimeMillis());
        int count = mWeatherDao.countAllFutureWeatherEntries(now);
        return (count < NetworkDataSource.NUM_MIN_DATA_COUNTS);
    }

    private boolean isFetchNeededCW() {
        long recentlyMills = System.currentTimeMillis() - DateUtils.MINUTE_IN_MILLIS * 30;
        Date dateRecently = new Date(recentlyMills);
        int count = mWeatherDao.countCurrentWeather(dateRecently);
//        if (BuildConfig.DEBUG) return true;
        return count <= 0;
    }

    private void startFetchWeatherService() {
        mNetworkDataSource.startFetchWeatherService();
    }

    private void startFetchCWeatherService() {
        mNetworkDataSource.startFetchCurrentWeatherService();
    }

    public LiveData<List<WeatherEntry>> getAllWeatherEntries() {
        return mWeatherDao.getAllEntries();
    }

    public LiveData<List<ListWeatherEntry>> getMidDayWeatherEntries() {
        long offset = Utils.getCityOffset();

        long daysSinceEpoch = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
        long tomorrowMidnightNormalizedUtc = (daysSinceEpoch + 1) * DAY_IN_MILLIS;

//        long tomorrowCityNoonUtc = tomorrowMidnightNormalizedUtc + 10 * HOUR_IN_MILLIS + offset


        return mWeatherDao.getMidDayForecast(new Date(tomorrowMidnightNormalizedUtc), offset, HOUR_IN_MILLIS);
    }
}