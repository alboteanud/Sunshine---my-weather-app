package com.craiovadata.android.sunshine.ui.main;

import com.craiovadata.android.sunshine.data.RepositoryWeather;
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry;
import com.craiovadata.android.sunshine.data.database.WeatherEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private final RepositoryWeather repository;
    private final LiveData<List<ListWeatherEntry>> forecast;
    private final LiveData<List<ListWeatherEntry>> forecastDays;
    private final LiveData<List<WeatherEntry>> allForecast;
    private final LiveData<List<WeatherEntry>> currentWeather;


    public MainActivityViewModel(RepositoryWeather repository) {
        this.repository = repository;
        forecast = this.repository.getWeatherForecast();
        forecastDays = this.repository.getWeatherForecastDays();
        allForecast = this.repository.getAllWeatherEntries();
        currentWeather = this.repository.getCurrentWeather();
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
