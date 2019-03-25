package com.example.android.sunshine.ui.main;

import com.example.android.sunshine.data.RepositoryWeather;
import com.example.android.sunshine.data.database.ListWeatherEntry;
import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final RepositoryWeather mRepository;
    private final LiveData<List<ListWeatherEntry>> forecast;
    private final LiveData<List<ListWeatherEntry>> forecastDays;
    private final LiveData<List<WeatherEntry>> allForecast;
    private final LiveData<List<WeatherEntry>> currentWeather;


    public MainActivityViewModel(RepositoryWeather repository) {
        mRepository = repository;
        forecast = mRepository.getWeatherForecast();
        forecastDays = mRepository.getWeatherForecastDays();
        allForecast = mRepository.getAllWeatherEntries();
        currentWeather = mRepository.getCurrentWeather();
    }

    public LiveData<List<ListWeatherEntry>> getForecast() {
        return forecast;
    }
    public LiveData<List<ListWeatherEntry>> getForecastDays() {
        return forecastDays;
    }

    public LiveData<List<WeatherEntry>> getAllForecast() {
        return allForecast;
    }

    public LiveData<List<WeatherEntry>> getCurrentWeather() {
        return currentWeather;
    }

}
