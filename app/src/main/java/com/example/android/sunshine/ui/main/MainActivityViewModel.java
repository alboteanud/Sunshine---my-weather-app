package com.example.android.sunshine.ui.main;

import com.example.android.sunshine.data.RepositoryWeather;
import com.example.android.sunshine.data.database.ListWeatherEntry;
import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final RepositoryWeather mRepository;
    private final LiveData<List<ListWeatherEntry>> mForecast;
    private final LiveData<WeatherEntry> currentWeather;

    public MainActivityViewModel(RepositoryWeather repository) {
        mRepository = repository;
        mForecast = mRepository.getWeatherForecasts();
        currentWeather = mRepository.getCurrentWeather();
    }

    public LiveData<List<ListWeatherEntry>> getForecast() {
        return mForecast;
    }

    public LiveData<WeatherEntry> getCurrentWeather() {
        return currentWeather;
    }

}
