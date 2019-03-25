package com.example.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.ui.main.MainActivity
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

    fun logEntry(context: Context, entry: WeatherEntry) {
        val simpleDateFormat = Utils.getFormatterCityTZ("dd MMM HH:mm")
        val date = simpleDateFormat.format(entry.date.time)
        val temperature = SunshineWeatherUtils.formatTemperature(context, entry.temperature)
        val wind = SunshineWeatherUtils.getFormattedWind(context, entry.wind, entry.degrees)
        Log.d(MainActivity.TAG, "$date  $temperature isCW-${entry.isCurrentWeather} " +
                "id-${entry.id} wind-$wind ")
    }

}