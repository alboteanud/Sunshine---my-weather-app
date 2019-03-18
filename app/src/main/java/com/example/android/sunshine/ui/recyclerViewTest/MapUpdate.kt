package com.example.android.sunshine.ui.recyclerViewTest

import com.example.android.sunshine.data.database.WeatherEntry

data class MapUpdate(val weatherEntry: WeatherEntry)
    : Update(weatherEntry.id, Update.TYPE.MAP, weatherEntry.date)