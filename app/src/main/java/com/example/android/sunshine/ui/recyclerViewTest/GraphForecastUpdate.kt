package com.example.android.sunshine.ui.recyclerViewTest

import com.example.android.sunshine.data.database.ListWeatherEntry

//(val weatherIconId: Int, val date: Date, val temp: Double, val icon: String)
data class GraphForecastUpdate(val list: MutableList<ListWeatherEntry>)
    : Update(list[0].id, Update.TYPE.GRAPH, list[0].date)

