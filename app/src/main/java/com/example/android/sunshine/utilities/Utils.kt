package com.example.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.ui.list.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    private val limit: Int
        get() {
            val dtMar = 1551458576000L

            val sinceMars = System.currentTimeMillis() - dtMar
            val daysSinceMars = TimeUnit.MILLISECONDS.toDays(sinceMars).toInt()
            val monthsSinceMars = daysSinceMars / 30
            var myValue = 2 + monthsSinceMars
            if (myValue > 20) myValue = 20
            return myValue
        }

    fun getAdBannerId(context: Context): String {
        val limit = limit
        val r = Random().nextInt(limit)

        var id = "ca-app-pub-3931793949981809/9792691746"
        if (r < 2) id = context.getString(R.string.banner_id_Petru)
        if (BuildConfig.DEBUG) id = "ca-app-pub-3940256099942544/6300978111"
        Log.d("tag", "limit: $limit r: $r \n$id")
        return id
    }


    fun getBackResId(context: Context): Int {
        val imgs = intArrayOf(R.drawable.c1, R.drawable.stabil1, R.drawable.c2, R.drawable.stabil2, R.drawable.c3, R.drawable.stabil3, R.drawable.c4, R.drawable.stabil4, R.drawable.c5, R.drawable.stabil5, R.drawable.c6, R.drawable.stabil6)
        val pref = context.getSharedPreferences("_", Context.MODE_PRIVATE)
        val wasInit = pref.getBoolean("init", false)
        if (!wasInit) {
            pref.edit().putBoolean("init", true).apply()
            return imgs[0]
        }
        val now = System.currentTimeMillis()
        val daysSinceEpoch = TimeUnit.MILLISECONDS.toDays(now)
        val n = (daysSinceEpoch % imgs.size).toInt()
        return imgs[n]
    }



    fun logDBvalues(context:  Context, weatherEntries: MutableList<ListWeatherEntry>) {
        Log.d(MainActivity.TAG, "Total values: " + weatherEntries.size)
        if (!BuildConfig.DEBUG) return
        weatherEntries.forEach {
            val mills = SunshineDateUtils.getCityTimeMills(context, it.date.time)
            val utcDt = SimpleDateFormat(" dd MMM HH:mm").format(it.date)
            val cityDt = SimpleDateFormat(" dd MMM HH:mm").format(mills)
            val temperature = SunshineWeatherUtils.formatTemperature(context, it.temp)
            Log.d(MainActivity.TAG, "DB entry: " + utcDt + "  cityDt " + cityDt + "  temp " + temperature)
        }
    }
}
