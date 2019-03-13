package com.example.android.sunshine.ui.list

import android.view.View
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.weather_card.view.*

object WeatherCardUtils {

    fun initWeatherCard(cardWeather: View, weatherEntries: WeatherEntry) {

        val entry = weatherEntries
        val temperature = entry.temp
        val dateInMillis = entry.date.time
        val iconId = entry.icon

        val highString = SunshineWeatherUtils.formatTemperature(cardWeather.context, temperature)
        cardWeather.temperature_main?.text = highString

        val dateString = SunshineDateUtils.getFriendlyDateString(cardWeather.context, dateInMillis, false)
        cardWeather.weatherDate?.text = dateString

        val weatherImageResourceId = SunshineWeatherUtils
                .getLargeArtResourceIdForIconCode(iconId)

        cardWeather.weatherIcon?.setImageResource(weatherImageResourceId)

        val weatherIconId = entry.weatherIconId
        val description = SunshineWeatherUtils.getStringForWeatherCondition(cardWeather.context, weatherIconId)
        cardWeather.weatherDescription?.text = description


//        cardView.setOnClickListener {
//            context.startActivity(Intent(context, DetailActivity::class.java))
//        }
    }
}
