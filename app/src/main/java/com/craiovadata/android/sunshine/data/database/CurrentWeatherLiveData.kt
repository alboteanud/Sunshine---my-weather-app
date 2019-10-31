package com.craiovadata.android.sunshine.data.database

import androidx.lifecycle.LiveData

class CurrentWeatherLiveData: LiveData<List<WeatherEntry>>() {

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }
}