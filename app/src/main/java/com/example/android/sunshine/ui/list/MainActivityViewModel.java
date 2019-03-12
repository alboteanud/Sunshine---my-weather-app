package com.example.android.sunshine.ui.list;

import com.example.android.sunshine.data.RepositoryWeather;
import com.example.android.sunshine.data.database.ListWeatherEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final RepositoryWeather mRepository;
    private final LiveData<List<ListWeatherEntry>> mForecast;
    private final LiveData<List<ListWeatherEntry>> currentWeather;



    public MainActivityViewModel(RepositoryWeather repository) {
        mRepository = repository;
        mForecast = mRepository.getWeatherForecasts();
        currentWeather = mRepository.getCurrentWeather();
    }

    public LiveData<List<ListWeatherEntry>> getForecast() {
        return mForecast;
    }

    public LiveData<List<ListWeatherEntry>> getCurrentWeather() {
        return currentWeather;
    }

}
