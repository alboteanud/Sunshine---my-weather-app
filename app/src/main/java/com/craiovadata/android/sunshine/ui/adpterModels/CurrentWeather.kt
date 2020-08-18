package com.craiovadata.android.sunshine.ui.adpterModels

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils.NO_DEGREE_WIND
import kotlinx.android.synthetic.main.current_weather_card.view.*
import kotlinx.android.synthetic.main.current_weather_card.view.wind_measurement
import java.util.*

//(val weatherId: Int, val date: Date, val temperature: Double, val iconCodeOWM: String)
data class CurrentWeather(val weatherEntry: WeatherEntry?) :
    Base(weatherEntry?.id ?: -1, TYPE.WEATHER, weatherEntry?.date ?: Date(0)) {

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
            val weatherDescription =
                SunshineWeatherUtils.getStringForWeatherCondition(cardView.context, weatherId)
            val weatherIconDescription =
                cardView.context.getString(R.string.a11y_forecast_icon, weatherDescription)

            cardView.weatherIcon.setImageResource(weatherImageId)
            cardView.weatherIcon.contentDescription = weatherIconDescription

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
//            if (BuildConfig.DEBUG) {
//            if (entry.cityName == "")
//                cardView.weatherDate.text = "city1 not found"
//            else {
//                val simpleDateFormat = CityData.getFormatterCityTZ("dd MMM HH:mm")
//                val myText = entry.cityName + "\n" + simpleDateFormat.format(entry.date.time)
//                cardView.weatherDate.text = myText
//                Toast.makeText(cardView.context, myText, Toast.LENGTH_SHORT).show()
//            }
//            } else {
//                val simpleDateFormat = CityData.getFormatterCityTZ("dd MMM")
//                val myText = simpleDateFormat.format(entry.date.time)
//                cardView.weatherDate.text = myText
//            }


            /***********************
             * Weather Description *
             */
            /* Use the weatherId to obtain the proper description */
            val description =
                SunshineWeatherUtils.getStringForWeatherCondition(cardView.context, weatherId)

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


            /****************************
             * Wind speed and direction *
             */
            val windSpeed = entry.wind
//            val windDirection = entry.degrees
            val windDirection = NO_DEGREE_WIND
            val windString =
                SunshineWeatherUtils.getFormattedWind(cardView.context, windSpeed, windDirection)
            val windA11y = cardView.context.getString(R.string.a11y_wind, windString)

            cardView.wind_measurement.text = windString
            cardView.wind_measurement.contentDescription = windA11y
//            cardView.wind_label.contentDescription = windA11y


            rotateMill(
                cardView,
                entry
            )

            cardView.wind_layout_current.setOnClickListener {
                val text = cardView.context.getString(R.string.wind_label)
                Toast.makeText(it.context, text, Toast.LENGTH_SHORT).show()
            }

        }

        private fun rotateMill(cardView: View, entry: WeatherEntry) {

            val millRotorView = cardView.findViewById(R.id.rotor_mill) as View? ?: return

            var endAngle = 360f
            if (entry.degrees < 180) endAngle = -endAngle
            val animator = ValueAnimator.ofFloat(0f, endAngle)
            animator.addUpdateListener { animation ->
                millRotorView.rotation = animation.animatedValue as Float
            }
            val normalisedWindSpeed =
                normalizeWind(
                    entry.wind
                )
//            val normalisedWindSpeed = 12.0
            val duration = 20000 / normalisedWindSpeed
            animator.duration = duration.toLong()
            animator.interpolator = null
            animator.repeatCount = Animation.INFINITE
            animator.start()
        }

        private fun normalizeWind(windSpeed: Double): Double {
            return if (windSpeed < 4) 4.0 else if (windSpeed > 12) 12.0 else windSpeed
        }
    }
}

