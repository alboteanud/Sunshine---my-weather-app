package com.craiovadata.android.sunshine.utilities

import android.content.Context
import androidx.preference.PreferenceManager
import android.util.Log
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.R

/**
 * Contains useful utilities for displaying weather forecasts, such as conversion between Celsius
 * and Fahrenheit, from kph to mph, and from degrees to NSEW.  It also contains the mapping of
 * weather condition codes in OpenWeatherMap to strings.  These strings are defined in
 * res/values/strings.xml
 */
object SunshineWeatherUtils {
    private val LOG_TAG = SunshineWeatherUtils::class.java.simpleName
    /**
     * Temperature data is stored in Celsius by our app. Depending on the user's preference,
     * the app may need to display the temperature in Fahrenheit. This method will perform that
     * temperature conversion if necessary. It will also format the temperature so that no
     * decimal points show. Temperatures will be formatted to the following form: "21°"
     *
     * @param context     Android Context to access preferences and resources
     * @param temperature Temperature in degrees Celsius (°C)
     * @return Formatted temperature String in the following form:
     * "21°"
     */
    fun formatTemperature(
        context: Context,
        temperature: Double
    ): String {
        val mTemperature = adaptTemperature(context, temperature)
        val roundedTemp = mTemperature.toInt().toFloat()
        val temperatureFormatResourceId = R.string.format_temperature
        /* For presentation, assume the user doesn't care about tenths of a degree. */return String.format(
            context.getString(temperatureFormatResourceId),
            roundedTemp
        )
    }

    fun adaptTemperature(
        context: Context,
        temperature: Double
    ): Double {
        if (isImperialSystem(context)) {
            return celsiusToFahrenheit(temperature)
        }
        return temperature
    }

    private fun isImperialSystem(context: Context): Boolean {
        val sp =
            PreferenceManager.getDefaultSharedPreferences(context)
        val key = context.getString(R.string.pref_units_key)
        val defaultImperial = CityData.IS_IMPERIAL_UNITS_DEFAULT
        return sp.getBoolean(key, defaultImperial)
    }

    private fun celsiusToFahrenheit(temperatureInCelsius: Double): Double {
        return temperatureInCelsius * 1.8 + 32
    }

    const val NO_DEGREE_WIND = 1.0
    fun getFormattedWind(
        context: Context,
        windSpeed_m_s: Double,
        degrees: Double
    ): String {
        var windFormat = R.string.format_wind_kmh
        var windSpeed = (windSpeed_m_s * 3.6f).toFloat() // transf in km/h
        if (isImperialSystem(context)) {
            windFormat = R.string.format_wind_mph
            windSpeed *= .6213f // transf in miles/hour
        }
        var direction = ""
        if (degrees == NO_DEGREE_WIND){
            direction = ""
        } else if (degrees >= 337.5 || degrees < 22.5) {
            direction = context.getString(R.string.N)
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direction =  context.getString(R.string.NE)
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direction = context.getString(R.string.E)
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direction =  context.getString(R.string.SE)
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direction =  context.getString(R.string.S)
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direction = context.getString(R.string.SW)
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direction = context.getString(R.string.W)
        } else if (degrees >= 292.5 && degrees < 337.5) {
            direction = context.getString(R.string.NW)
        }
        return String.format(context.getString(windFormat), windSpeed, direction)
    }

    /**
     * Helper method to provide the string according to the weather
     * condition _id returned by the OpenWeatherMap call.
     *
     * @param context   Android context
     * @param weatherId from OpenWeatherMap API response
     * See http://openweathermap.org/weather-conditions for a list of all IDs
     * @return String for the weather condition, null if no relation is found.
     */
    fun getStringForWeatherCondition(
        context: Context,
        weatherId: Int
    ): String {
        val stringId: Int = if (weatherId in 200..232) {
            R.string.condition_2xx
        } else if (weatherId in 300..321) {
            R.string.condition_3xx
        } else when (weatherId) {
            500 -> R.string.condition_500
            501 -> R.string.condition_501
            502 -> R.string.condition_502
            503 -> R.string.condition_503
            504 -> R.string.condition_504
            511 -> R.string.condition_511
            520 -> R.string.condition_520
            521 -> R.string.condition_521
            522 -> R.string.condition_522
            531 -> R.string.condition_531
            600 -> R.string.condition_600
            601 -> R.string.condition_601
            602 -> R.string.condition_602
            611 -> R.string.condition_611
            612 -> R.string.condition_612
            613 -> R.string.condition_613
            615 -> R.string.condition_615
            616 -> R.string.condition_616
            620 -> R.string.condition_620
            621 -> R.string.condition_621
            622 -> R.string.condition_622
            701 -> R.string.condition_701
            711 -> R.string.condition_711
            721 -> R.string.condition_721
            731 -> R.string.condition_731
            741 -> R.string.condition_741
            751 -> R.string.condition_751
            761 -> R.string.condition_761
            762 -> R.string.condition_762
            771 -> R.string.condition_771
            781 -> R.string.condition_781
            800 -> R.string.condition_800
            801 -> R.string.condition_801
            802 -> R.string.condition_802
            803 -> R.string.condition_803
            804 -> R.string.condition_804
            900 -> R.string.condition_900
            901 -> R.string.condition_901
            902 -> R.string.condition_902
            903 -> R.string.condition_903
            904 -> R.string.condition_904
            905 -> R.string.condition_905
            906 -> R.string.condition_906
            951 -> R.string.condition_951
            952 -> R.string.condition_952
            953 -> R.string.condition_953
            954 -> R.string.condition_954
            955 -> R.string.condition_955
            956 -> R.string.condition_956
            957 -> R.string.condition_957
            958 -> R.string.condition_958
            959 -> R.string.condition_959
            960 -> R.string.condition_960
            961 -> R.string.condition_961
            962 -> R.string.condition_962
            else -> return context.getString(R.string.condition_unknown, weatherId)
        }
        return context.getString(stringId)
    }

    /**
     * Helper method to provide the iconCodeOWM resource _id according to the weather condition _id returned
     * by the OpenWeatherMap call. This method is very similar to
     *
     *
     * [.getLargeArtResourceIdForWeatherCondition].
     *
     *
     * The difference between these two methods is that this method provides smaller assets, used
     * in the list item layout for a "future day", as well as
     *
     * @param weatherId from OpenWeatherMap API response
     * See http://openweathermap.org/weather-conditions for a list of all IDs
     * @return resource _id for the corresponding iconCodeOWM. -city_1 if no relation is found.
     */
    fun getSmallArtResourceIdForWeatherCondition(weatherId: Int): Int { /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId in 200..232) {
            return R.drawable.ic_storm
        } else if (weatherId in 300..321) {
            return R.drawable.ic_light_rain
        } else if (weatherId in 500..504) {
            return R.drawable.ic_rain
        } else if (weatherId == 511) {
            return R.drawable.ic_snow
        } else if (weatherId in 520..531) {
            return R.drawable.ic_rain
        } else if (weatherId in 600..622) {
            return R.drawable.ic_snow
        } else if (weatherId in 701..761) {
            return R.drawable.ic_fog
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm
        } else if (weatherId == 800) {
            return R.drawable.ic_clear
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds
        } else if (weatherId in 802..804) {
            return R.drawable.ic_cloudy
        } else if (weatherId in 900..906) {
            return R.drawable.ic_storm
        } else if (weatherId in 958..962) {
            return R.drawable.ic_storm
        } else if (weatherId in 951..957) {
            return R.drawable.ic_clear
        }
        Log.e(LOG_TAG, "Unknown Weather: $weatherId")
        return R.drawable.ic_storm
    }

    fun getSmallArtResourceIdForIconCode(iconCode: String): Int {
        when (iconCode) {
            "01d" -> return R.drawable.ic_clear
            "01n" -> return R.drawable.ic_clear_n
            "02d" -> return R.drawable.ic_light_clouds
            "02n" -> return R.drawable.ic_light_clouds_n
            "03d", "03n", "04d", "04n" -> return R.drawable.ic_cloudy
            "09d", "09n" -> return R.drawable.ic_light_rain
            "10d", "10n" -> return R.drawable.ic_rain
            "11d", "11n" -> return R.drawable.ic_storm
            "13d", "13n" -> return R.drawable.ic_snow
            "50d", "50n" -> return R.drawable.ic_fog
        }
        Log.e(LOG_TAG, "Unknown Weather: $iconCode")
        return R.drawable.ic_storm
    }

    fun getLargeArtResourceIdForIconCode(iconCode: String): Int {
        when (iconCode) {
            "01d" -> return R.drawable.art_clear
            "01n" -> return R.drawable.art_clear_n
            "02d" -> return R.drawable.art_light_clouds
            "02n" -> return R.drawable.art_light_clouds_n
            "03d", "03n", "04d", "04n" -> return R.drawable.art_clouds
            "09d", "09n" -> return R.drawable.art_light_rain
            "10d", "10n" -> return R.drawable.art_rain
            "11d", "11n" -> return R.drawable.art_storm
            "13d", "13n" -> return R.drawable.art_snow
            "50d", "50n" -> return R.drawable.art_fog
        }
        Log.e(LOG_TAG, "Unknown Weather: $iconCode")
        return R.drawable.art_storm
    }

    /**
     * Helper method to provide the art resource ID according to the weather condition ID returned
     * by the OpenWeatherMap call. This method is very similar to
     *
     *
     * [.getSmallArtResourceIdForWeatherCondition].
     *
     *
     * The difference between these two methods is that this method provides larger assets, used
     * in the "today view" of the list, as well as in the DetailActivity.
     *
     * @param weatherId from OpenWeatherMap API response
     * See http://openweathermap.org/weather-conditions for a list of all IDs
     * @return resource ID for the corresponding iconCodeOWM. -city_1 if no relation is found.
     */
    fun getLargeArtResourceIdForWeatherCondition(weatherId: Int): Int { /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId in 200..232) {
            return R.drawable.art_storm
        } else if (weatherId in 300..321) {
            return R.drawable.art_light_rain
        } else if (weatherId in 500..504) {
            return R.drawable.art_rain
        } else if (weatherId == 511) {
            return R.drawable.art_snow
        } else if (weatherId in 520..531) {
            return R.drawable.art_rain
        } else if (weatherId in 600..622) {
            return R.drawable.art_snow
        } else if (weatherId in 701..761) {
            return R.drawable.art_fog
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.art_storm
        } else if (weatherId == 800) {
            return R.drawable.art_clear
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds
        } else if (weatherId in 802..804) {
            return R.drawable.art_clouds
        } else if (weatherId in 900..906) {
            return R.drawable.art_storm
        } else if (weatherId in 958..962) {
            return R.drawable.art_storm
        } else if (weatherId in 951..957) {
            return R.drawable.art_clear
        }
        Log.e(LOG_TAG, "Unknown Weather: $weatherId")
        return R.drawable.art_storm
    }


}