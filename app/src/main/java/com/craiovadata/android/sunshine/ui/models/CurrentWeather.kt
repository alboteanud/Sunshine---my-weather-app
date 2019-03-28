package com.craiovadata.android.sunshine.ui.models

import android.view.View
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import com.craiovadata.android.sunshine.utilities.Utils
import kotlinx.android.synthetic.main.current_weather_card.view.*
import java.util.*

//(val weatherId: Int, val date: Date, val temperature: Double, val iconCodeOWM: String)
data class CurrentWeather(val weatherEntry: WeatherEntry?)
    : Base(weatherEntry?.id ?: -1, Base.TYPE.WEATHER, weatherEntry?.date ?: Date(0)) {

    companion object {

        @JvmStatic
        fun bindWeatherToUI(entry: WeatherEntry?, cardView: View) {
            if (entry == null) return
            /****************
             * Weather Icon *
             */
            val weatherId = entry.weatherId
            //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
            val iconId = entry.iconCodeOWM
//            val iconId = "01n"
            val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(iconId)

            /* Set the resource ID on the iconCodeOWM to display the art */
            cardView.weatherIcon.setImageResource(weatherImageId)

            /****************
             * Weather Date *
             */
            /*
             * The _date that is stored is a GMT representation at midnight of the _date when the weather
             * information was loaded for.
             *
             * When displaying this _date, one must add the GMT offset (in milliseconds) to acquire
             * the _date representation for the local _date in local time.
             * SunshineDateUtils#getFriendlyDateString takes care of this for us.
             */
//        if (BuildConfig.DEBUG){
            val simpleDateFormat = Utils.getFormatterCityTZ("dd MMM HH:mm")
            val dateTxt = simpleDateFormat.format(entry.date.time)
            cardView.weatherDate.text = dateTxt
//        }


            /***********************
             * Weather Description *
             */
            /* Use the weatherId to obtain the proper description */
            val description = SunshineWeatherUtils.getStringForWeatherCondition(cardView.context, weatherId)

            /* Create the accessibility (a11y) String from the weather description */
            val descriptionA11y = cardView.context.getString(R.string.a11y_forecast, description)

            /* Set the text and content description (for accessibility purposes) */
            cardView.weatherDescription.text = description
            cardView.weatherDescription.contentDescription = descriptionA11y

            /* Set the content description on the weather image (for accessibility purposes) */
            cardView.weatherIcon.contentDescription = descriptionA11y

            /**************************
             * High (max) temperature *
             */

            val maxInCelsius = entry.temperature

            /*
             * If the user's preference for weather is fahrenheit, formatTemperature will convert
             * the temperature. This method will also append either °C or °F to the temperature
             * String.
             */
            val highString = SunshineWeatherUtils.formatTemperature(cardView.context, maxInCelsius)

            /* Create the accessibility (a11y) String from the weather description */
            val highA11y = cardView.context.getString(R.string.a11y_high_temp, highString)

            /* Set the text and content description (for accessibility purposes) */
            cardView.temperatureText.text = highString
            cardView.temperatureText.contentDescription = highA11y

        }
    }
}

