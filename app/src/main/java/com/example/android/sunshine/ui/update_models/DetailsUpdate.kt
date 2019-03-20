package com.example.android.sunshine.ui.update_models

import com.example.android.sunshine.data.database.WeatherEntry

data class DetailsUpdate(val weatherEntry: WeatherEntry)
    : BaseUpdate(weatherEntry.id, BaseUpdate.TYPE.DETAILS, weatherEntry.date)