package com.example.android.sunshine.ui.cards

import android.view.View
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.details_weather_card.view.*

object WeatherDetailsCardUtils {

    fun bindWeatherToUI(entry: WeatherEntry, cardView: View) {

        /************
         * Humidity *
         */

        val humidity = entry.humidity
        val humidityString = cardView.context.getString(R.string.format_humidity, humidity)
        val humidityA11y = cardView.context.getString(R.string.a11y_humidity, humidityString)

        /* Set the text and content description (for accessibility purposes) */
        cardView.humidity_measurement.text = humidityString
        cardView.humidity_measurement.contentDescription = humidityA11y

        cardView.humidity_label.contentDescription = humidityA11y

        /****************************
         * Wind speed and direction *
         */
        val windSpeed = entry.wind
        val windDirection = entry.degrees
        val windString = SunshineWeatherUtils.getFormattedWind(cardView.context, windSpeed, windDirection)
        val windA11y = cardView.context.getString(R.string.a11y_wind, windString)

        cardView.wind_measurement.text = windString
        cardView.wind_measurement.contentDescription = windA11y
        cardView.wind_label.contentDescription = windA11y


        val pressure = entry.pressure

        /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        val pressureString = cardView.context.getString(R.string.format_pressure, pressure)

        val pressureA11y = cardView.context.getString(R.string.a11y_pressure, pressureString)

        /* Set the text and content description (for accessibility purposes) */
        cardView.pressure_details.text = pressureString
        cardView.pressure_details.contentDescription = pressureA11y
        cardView.pressure_label.contentDescription = pressureA11y


    }


}
