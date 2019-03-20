package com.example.android.sunshine.ui.update_models

import com.example.android.sunshine.data.database.ListWeatherEntry

//(val weatherIconId: Int, val date: Date, val temp: Double, val icon: String)
data class GraphForecastUpdate(val list: MutableList<ListWeatherEntry>)
    : BaseUpdate(list[0].id, BaseUpdate.TYPE.GRAPH, list[0].date)

