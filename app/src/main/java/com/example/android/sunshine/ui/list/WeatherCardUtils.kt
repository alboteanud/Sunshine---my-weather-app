package com.example.android.sunshine.ui.list

import androidx.cardview.widget.CardView
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.weather_card.view.*

object WeatherCardUtils {

    fun initWeatherCard(cardWeather: CardView, weatherEntries: MutableList<ListWeatherEntry>) {
        val context = cardWeather.context
        val entry = weatherEntries[0]
        val temperature = entry.temp
        val dateInMillis = entry.date.time
        val iconId = entry.icon

        val highString = SunshineWeatherUtils.formatTemperature(context, temperature)
        cardWeather.title.text = highString


        val dateString = SunshineDateUtils.getFriendlyDateString(context, dateInMillis, false)
        cardWeather.weatherDate.text = dateString

        val weatherImageResourceId = SunshineWeatherUtils
                .getLargeArtResourceIdForIconCode(iconId)

        cardWeather.weatherIcon.setImageResource(weatherImageResourceId)

        val weatherIconId = entry.weatherIconId
        val description = SunshineWeatherUtils.getStringForWeatherCondition(context, weatherIconId)
        cardWeather.weatherDescription.text = description
    }
}
