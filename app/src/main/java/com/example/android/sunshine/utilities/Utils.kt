package com.example.android.sunshine.utilities

import android.content.Context
import android.util.Log
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.R
import com.example.android.sunshine.TimeZone.TIME_ZONE
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.data.database.WeatherEntry
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DST_OFFSET
import java.util.Calendar.ZONE_OFFSET
import java.util.TimeZone.getTimeZone
import java.util.concurrent.TimeUnit

object Utils {

    private val images = intArrayOf(
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.stabil1,
            R.drawable.stabil2,
            R.drawable.stabil3,
            R.drawable.stabil4,
            R.drawable.stabil5,
            R.drawable.stabil6)

    @JvmStatic
    fun getCityTimeZone(): TimeZone? {
        return getTimeZone(TIME_ZONE)
    }

    @JvmStatic
    fun getCityOffset(): Long {
        val cal = GregorianCalendar()
        cal.timeZone = getCityTimeZone()
        return (cal.get(ZONE_OFFSET) + cal.get(DST_OFFSET)).toLong()
    }

    @JvmStatic
    fun getFormatterCityTZ(pattern: String): SimpleDateFormat {
        val cal = GregorianCalendar()
        cal.timeZone = getCityTimeZone()
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        simpleDateFormat.calendar = cal
        return simpleDateFormat
    }



    fun getBackResId(): Int {
        val now = System.currentTimeMillis()
        var hoursSinceEpoch = TimeUnit.MILLISECONDS.toHours(now)
        if (BuildConfig.DEBUG) hoursSinceEpoch = TimeUnit.MILLISECONDS.toMinutes(now)
        val n = (hoursSinceEpoch % images.size).toInt()
        return images[n]
    }

    fun logDBvalues(context: Context, forecastEntries: MutableList<ListWeatherEntry>, cwEntries: MutableList<WeatherEntry>) {
        if (!BuildConfig.DEBUG) return
        val simpleDateFormat = Utils.getFormatterCityTZ("dd MMM HH:mm")
        forecastEntries.forEach {
            val date = simpleDateFormat.format(it.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, it.temperature)
            Log.d("tag", "forecastEntry  $date $temperature")
        }
        cwEntries.forEach {
            val date = simpleDateFormat.format(it.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, it.temperature)
            Log.d("tag", "cw entry $date $temperature")
        }
    }

    private fun getAdDaysLimit(): Int {
        val dtMar = 1551458576000L
        val sinceMars = System.currentTimeMillis() - dtMar
        val daysSinceMars = TimeUnit.MILLISECONDS.toDays(sinceMars).toInt()
        val monthsSinceMars = daysSinceMars / 30
        var myValue = 2 + monthsSinceMars
        if (myValue > 20) myValue = 20
        return myValue
    }

    fun getAdBannerId(context: Context): String {
        val max = getAdDaysLimit()
        val r = Random().nextInt(max)
//        Log.d("tag", "limit: $limit r: $r \n$id")
        if (BuildConfig.DEBUG) return "ca-app-pub-3940256099942544/6300978111"
        if (r < 2) return context.getString(R.string.banner_id_Petru)
        return "ca-app-pub-3931793949981809/9792691746" // default
    }

}
