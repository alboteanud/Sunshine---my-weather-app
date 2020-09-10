package com.craiovadata.android.sunshine.ui.adpterModels

import android.content.Context
import android.os.Handler
import android.text.format.DateFormat
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry.Companion.CURRENT_WEATHER
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.details_weather_card.view.*
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt


data class Details(val weatherEntry: WeatherEntry) :
    Base(
//        weatherEntry.date.time,
        TYPE.DETAILS) {

    companion object {

        @JvmStatic
        fun bindWeatherToUI(entry: WeatherEntry?, cardView: View) {
            if (entry == null) return
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
            val windString =
                SunshineWeatherUtils.getFormattedWind(cardView.context, windSpeed, windDirection)
            val windA11y = cardView.context.getString(R.string.a11y_wind, windString)

            cardView.wind_measurement.text = windString
            cardView.wind_measurement.contentDescription = windA11y
            cardView.wind_label.contentDescription = windA11y


            // pressure
            /*
             * Format the pressure text using string resources. The reason we directly access
             * resources using getString rather than using a method from SunshineWeatherUtils as
             * we have for other data displayed in this Activity is because there is no
             * additional logic that needs to be considered in order to properly display the
             * pressure.
             */
            val pressure = entry.pressure
            val pressureString = cardView.context.getString(R.string.format_pressure, pressure)
            val pressureA11y = cardView.context.getString(R.string.a11y_pressure, pressureString)

            /* Set the text and content description (for accessibility purposes) */
            cardView.pressure_details.text = pressureString
            cardView.pressure_details.contentDescription = pressureA11y
            cardView.pressure_label.contentDescription = pressureA11y

            positionSunOnHorizont(
                entry,
                cardView
            )

//            h.post(myFunc(entry, cardView))
        }

        private fun myFunc(entry: WeatherEntry, cardView: View): Runnable {
            return object : Runnable {

                override fun run() {
                    positionSunOnHorizont(
                        entry,
                        cardView
                    )
                    h.removeCallbacksAndMessages(null)
                    h.postDelayed(this, 500)
                }
            }
        }

        val h = Handler()

        private fun positionSunOnHorizont(
            entry: WeatherEntry,
            cardView: View
        ) {

            val sunriseMillsUTC = entry.sunrise * 1000
            val sunsetMillsUTC = entry.sunset * 1000
//            val dt = entry.dt * 1000
            val sunPosition: Int =
                getDayStatus(
                    sunriseMillsUTC,
                    sunsetMillsUTC
                )

            if (entry.isCurrentWeather != CURRENT_WEATHER
                || (sunPosition != DAY)// && !BuildConfig.DEBUG)
            ) {
                // hide Sunshine layout
                cardView.findViewById<View>(R.id.sunsetSunriseLayout).visibility = View.GONE
                return
            }

            val horizontWidth = cardView.context.resources.getDimension(R.dimen.icon_horizont_width)
//            val horizontHeight = cardView.context.resources.getDimension(R.dimen.icon_horizont_height)
            val sunDim = cardView.context.resources.getDimension(R.dimen.icon_sun_size)
//            val padding = cardView.context.resources.getDimensionPixelSize(R.dimen.sunriseLayoutPadding)
            val sunLightPeriod = sunsetMillsUTC - sunriseMillsUTC

//            val addedHoursTest = (currentTimeMillis() % (12 * 1000)) / 1000
            val timeSinceSunrise =
                currentTimeMillis() - sunriseMillsUTC // + 12 * HOUR_MILLIS   //  + addedHoursTest * HOUR_MILLIS
            val passedDayRatio = timeSinceSunrise.toFloat() / sunLightPeriod
//            if (BuildConfig.DEBUG) passedDayProcentage = 1 / 2f
            val x = passedDayRatio * horizontWidth
//            val y = (x / 12.1f - 10).pow(2) + 50
            val y = ((passedDayRatio * 1.3f - 0.65f).pow(2) + 0.21f) * horizontWidth

            val sunView = cardView.findViewById<View>(R.id.icon_sun)
            sunView.x = x - sunDim / 2 //+ padding
            sunView.y = y - sunDim / 2

            val localFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), "HH mm")
            val citySunrise: Long =
                sunriseMillsUTC + getCityOffsetMillis() - getDeviceUTCoffset() // sdf aduce timp de pe disp  >> - DateUtils.getDeviceUTCoffset()
            val citySunset: Long = sunsetMillsUTC + getCityOffsetMillis() - getDeviceUTCoffset()
            val readableSunrise =
                SimpleDateFormat(localFormat, Locale.getDefault()).format(citySunrise)
            val readableSunset =
                SimpleDateFormat(localFormat, Locale.getDefault()).format(citySunset)
            cardView.findViewById<TextView>(R.id.sunriseTextView).text = readableSunrise
            cardView.findViewById<TextView>(R.id.sunsetTextView).text = readableSunset

            cardView.findViewById<View>(R.id.sunsetSunriseLayout).visibility = View.VISIBLE
        }

        private fun getCityOffsetMillis(): Long {
            return TimeZone.getTimeZone(CityData.TIME_ZONE_ID).getOffset(currentTimeMillis())
                .toLong()
        }

        private fun getDeviceUTCoffset(): Long {
            return TimeZone.getDefault().getOffset(currentTimeMillis()).toLong()
        }

        fun dpToPx(context: Context, dp: Int): Int {
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
        }

        private const val HOUR_MILLIS = 1000 * 60 * 60
        private const val DAY = 1
        private const val NIGHT = 2
        private const val EARLY_MORNING = 0

        private fun getDayStatus(sunriseMillsUTC: Long, sunsetMillsUTC: Long): Int {
            //        if (BuildConfig.DEBUG) return 1;
            val nowUtc = currentTimeMillis()  // - 12 * HOUR_MILLIS
            if (nowUtc > sunsetMillsUTC + HOUR_MILLIS)
                return NIGHT
            return if (nowUtc > sunriseMillsUTC - 4 * HOUR_MILLIS) DAY
            else EARLY_MORNING
        }

    }
}