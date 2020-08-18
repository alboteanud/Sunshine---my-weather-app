package com.craiovadata.android.sunshine.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.craiovadata.android.sunshine.data.database.Repository
import com.craiovadata.android.sunshine.ui.models.ListWeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.LifecycleObserver
import com.craiovadata.android.sunshine.ui.models.ListWebcamEntry
import com.craiovadata.android.sunshine.ui.models.WebcamEntry

class MyViewModel internal constructor(private val repository: Repository) : ViewModel(),
    LifecycleObserver {
    //    val nextHoursWeather: LiveData<List<ListWeatherEntry>> = repository.nextHoursWeather
    val midDayWeather: LiveData<List<ListWeatherEntry>> = repository.dayWeatherEntries
    val webcams: LiveData<List<WebcamEntry>> = repository.webcamsEntries


    // are rol la afisare
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    internal fun onStartEvent() {
        Log.i("MainVieModel", "Observer ON_RESUME")
        searchCurrentWeatherByTimestamp(System.currentTimeMillis()+ 3000)
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


