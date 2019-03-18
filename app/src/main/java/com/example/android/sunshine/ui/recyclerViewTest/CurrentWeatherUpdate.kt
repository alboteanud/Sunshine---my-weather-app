package com.example.android.sunshine.ui.recyclerViewTest

import com.example.android.sunshine.data.database.WeatherEntry

//(val weatherIconId: Int, val date: Date, val temp: Double, val icon: String)
data class CurrentWeatherUpdate(val weatherEntry: WeatherEntry)
    : Update(weatherEntry.id, Update.TYPE.WEATHER, weatherEntry.date)

