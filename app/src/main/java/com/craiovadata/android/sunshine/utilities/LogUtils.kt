package com.craiovadata.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.main.MainActivity

object LogUtils {

    @JvmStatic
    fun logEntries(context: Context, entries: List<WeatherEntry>) {
        if (!BuildConfig.DEBUG) return
        val simpleDateFormat = CityUtils.getFormatterCityTZ("HH:mm  dd MMM")

        entries.forEachIndexed { i, entry ->
            val date = simpleDateFormat.format(entry.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, entry.temperature)
            Log.d(
                MainActivity.TAG, "entry[$i] $date  $temperature isCW-${entry.isCurrentWeather} " +
                        "id-${entry.id} "
            )
        }
    }

}