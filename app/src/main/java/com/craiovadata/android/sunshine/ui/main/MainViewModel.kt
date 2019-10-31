package com.craiovadata.android.sunshine.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.craiovadata.android.sunshine.data.RepositoryWeather
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.LifecycleObserver

class MainViewModel internal constructor(private val repository: RepositoryWeather) : ViewModel(),
    LifecycleObserver {
    //    val nextHoursWeather: LiveData<List<ListWeatherEntry>> = repository.nextHoursWeather
    val midDayWeather: LiveData<List<ListWeatherEntry>> = repository.dayWeatherEntries

    fun onPolicyPressed() {
        repository.forceFetchCurrentWeather()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onStartEvent() {
        Log.i("MainVieModel", "Observer ON_START")
        searchCurrentWeatherByTimestamp(System.currentTimeMillis())
    }

    private val mutableTimestamp: MutableLiveData<Long> = MutableLiveData()

    private fun searchCurrentWeatherByTimestamp(timestamp: Long) {
        mutableTimestamp.value = timestamp
    }

    val currentWeatherObservable: LiveData<List<WeatherEntry>> =
        Transformations.switchMap(mutableTimestamp) { timestamp ->
            repository.getCurrentWeather(timestamp)
        }

    val nextHoursWeatherObservable: LiveData<List<ListWeatherEntry>> =
        Transformations.switchMap(mutableTimestamp) { timestamp ->
            repository.getWeatherNextHours(timestamp)
        }



}


