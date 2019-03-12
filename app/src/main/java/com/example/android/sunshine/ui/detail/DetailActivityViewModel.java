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

package com.example.android.sunshine.ui.detail;

import com.example.android.sunshine.data.RepositoryWeather;
import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * {@link ViewModel} for {@link DetailActivity}
 */
public class DetailActivityViewModel extends ViewModel {

    // Weather forecast the user is looking at
    private final LiveData<WeatherEntry> mWeather;

    // Date for the weather forecast
   private final Date mDate;
    private final RepositoryWeather mRepository;

    public DetailActivityViewModel(RepositoryWeather repository, Date date) {
                mRepository = repository;
                mDate = date;
                mWeather = mRepository.getWeatherByDate(mDate);
    }

    public LiveData<WeatherEntry> getWeather() {
                return mWeather;
    }

}
