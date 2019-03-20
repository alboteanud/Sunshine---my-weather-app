package com.example.android.sunshine.ui.update_models

import com.example.android.sunshine.data.database.WeatherEntry
import java.util.*

data class MapUpdate(val weatherEntry: WeatherEntry)
    : BaseUpdate(-4, BaseUpdate.TYPE.MAP, Date(0))