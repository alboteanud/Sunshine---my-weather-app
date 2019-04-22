package com.craiovadata.android.sunshine.ui.models

import android.view.LayoutInflater
import android.view.View
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import com.craiovadata.android.sunshine.utilities.Utils
import kotlinx.android.synthetic.main.current_weather_card.view.*
import kotlinx.android.synthetic.main.multi_day_card.view.*

data class MultiDay(val list: MutableList<ListWeatherEntry>?)
    : Base(list?.get(0)?.id, Base.TYPE.DAYS, list?.get(0)?.date) {

    companion object {

        @JvmStatic
        fun bindForecastToUI(weatherEntries: MutableList<ListWeatherEntry>?, view: View) {
            if (weatherEntries == null || weatherEntries.size <= 0) return

            val container = view.daysLayout
            if (container.childCount > 0) {
                container.removeAllViews()
            }
//        if (adView?.parent != null) (adView.parent as ViewGroup).removeView(adView)

            weatherEntries.forEach { entry ->
                val dayView = LayoutInflater.from(view.context).inflate(R.layout.days_weather_item, container, false)
                dayView?.let {
                    bindDayToUI(entry, dayView)
                    container.addView(dayView)
                }
            }
        }

        private fun bindDayToUI(entry: ListWeatherEntry, dayView: View) {
            /****************
             * Weather Icon *
             */
        val weatherId = entry.weatherId
            //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
            val iconId = entry.iconCodeOWM
            val weatherImageId = SunshineWeatherUtils.getSmallArtResourceIdForIconCode(iconId)

            /* Set the resource ID on the iconCodeOWM to display the art */
            dayView.weatherIcon.setImageResource(weatherImageId)

            val weatherDescription = SunshineWeatherUtils.getStringForWeatherCondition(dayView.context, weatherId)
            val weatherIconDescription = dayView.context.getString(R.string.a11y_forecast_icon, weatherDescription)
            dayView.weatherIcon.contentDescription = weatherIconDescription

            /****************
             * Weather Date *
             */
            var pattern = "EEEE"
            if (BuildConfig.DEBUG) pattern = "EEE dd MMM HH.mm"
            val simpleDateFormat = Utils.getFormatterCityTZ(pattern)
            val dateTxt = simpleDateFormat.format(entry.date.time)
            dayView.weatherDate.text = dateTxt

            /**************************
             * High (max) temperature *
             */

            val maxInCelsius = entry.temperature

            /*
             * If the user's preference for weather is fahrenheit, formatTemperature will convert
             * the temperature. This method will also append either °C or °F to the temperature
             * String.
             */
            val highString = SunshineWeatherUtils.formatTemperature(dayView.context, maxInCelsius)

            /* Create the accessibility (a11y) String from the weather description */
            val highA11y = dayView.context.getString(R.string.a11y_high_temp, highString)

            /* Set the text and content description (for accessibility purposes) */
            dayView.temperatureText.text = highString
            dayView.temperatureText.contentDescription = highA11y
        }

    }

}

