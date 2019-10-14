package com.craiovadata.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*

object LogUtils {

    @JvmStatic
    fun saveDateToPrefs(context: Context) {
        if (!BuildConfig.DEBUG) return
        val prefs = context.getSharedPreferences("_", Context.MODE_PRIVATE)
        var txt = prefs.getString("txt", "")
        val now = System.currentTimeMillis()
        val date = SimpleDateFormat("HH:mm  dd MMM", Locale.getDefault()).format(now)
        txt?.let {
            txt += date + "\n"
            prefs.edit().putString("txt", txt).apply()
        }
    }

    @JvmStatic
    fun logResponse(logTag: String, entry: WeatherEntry) {
        if (!BuildConfig.DEBUG) return
        Log.d(logTag, String.format("First value is %1.0f, _date %s  iconCodeOWM %s",
                entry.temperature,
                entry.date,
                entry.iconCodeOWM))

    }

    @JvmStatic
    fun logEntry(context: Context, entry: WeatherEntry) {
        if (!BuildConfig.DEBUG) return
        val simpleDateFormat = Utils.getFormatterCityTZ("dd MMM HH:mm")
        val date = simpleDateFormat.format(entry.date.time)
        val temperature = SunshineWeatherUtils.formatTemperature(context, entry.temperature)
        val wind = SunshineWeatherUtils.getFormattedWind(context, entry.wind, entry.degrees)
        Log.d(MainActivity.TAG, "$date  $temperature isCW-${entry.isCurrentWeather} " +
                "id-${entry.id} ")
    }

    @JvmStatic
    fun logDBvalues(context: Context, forecastEntries: MutableList<ListWeatherEntry>, cwEntries: MutableList<WeatherEntry>) {
        if (!BuildConfig.DEBUG) return
        val simpleDateFormat = Utils.getFormatterCityTZ("dd MMM HH:mm")
        forecastEntries.forEach {
            val date = simpleDateFormat.format(it.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, it.temperature)
            Log.d("LOG_UTILS", "forecastEntry  $date $temperature")
        }
        cwEntries.forEach {
            val date = simpleDateFormat.format(it.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, it.temperature)
            Log.d("LOG_UTILS", "cw entry $date $temperature")
        }
    }

}