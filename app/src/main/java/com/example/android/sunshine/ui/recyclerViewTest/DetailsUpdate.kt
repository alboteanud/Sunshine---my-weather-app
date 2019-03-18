package com.example.android.sunshine.ui.recyclerViewTest

import com.example.android.sunshine.data.database.WeatherEntry

data class DetailsUpdate(val weatherEntry: WeatherEntry)
    : Update(weatherEntry.id, Update.TYPE.DETAILS, weatherEntry.date)