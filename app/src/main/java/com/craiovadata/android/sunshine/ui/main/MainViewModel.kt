package com.craiovadata.android.sunshine.ui.main

import com.craiovadata.android.sunshine.data.RepositoryWeather
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel internal constructor(private val repository: RepositoryWeather) : ViewModel() {
    val nextHoursWeather: LiveData<List<ListWeatherEntry>> = repository.nextHoursWeather
    val midDayWeather: LiveData<List<ListWeatherEntry>> = repository.dayWeatherEntries
    var currentWeather: LiveData<List<WeatherEntry>> = repository.currentWeather

    fun onRestartActivity() {
        repository.fetchCurrentWeatherIfNeeded()
    }

    fun onPolicyPressed() {
        repository.insertFakeDataCW()
    }

}
