package com.example.android.sunshine.ui.update_models

import com.example.android.sunshine.data.database.WeatherEntry

//(val weatherIconId: Int, val date: Date, val temp: Double, val icon: String)
data class CurrentWeatherUpdate(val weatherEntry: WeatherEntry)
    : BaseUpdate(weatherEntry.id, BaseUpdate.TYPE.WEATHER, weatherEntry.date)

