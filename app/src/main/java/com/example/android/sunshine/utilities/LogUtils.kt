package com.example.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.data.database.WeatherEntry
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
        txt += date + "\n"
        prefs.edit().putString("txt", txt).apply()

    }

    @JvmStatic
    fun logResponse(logTag: String, entry: WeatherEntry) {
        if (!BuildConfig.DEBUG) return
        Log.d(logTag, String.format("First value is %1.0f, _date %s  icon %s",
                entry.temp,
                entry.date,
                entry.icon))

    }

}