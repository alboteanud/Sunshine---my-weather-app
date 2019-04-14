package com.craiovadata.android.sunshine.utilities

import android.content.Context
import android.text.util.Linkify
import android.widget.TextView
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.MyTimeZone.TIME_ZONE
import com.craiovadata.android.sunshine.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DST_OFFSET
import java.util.Calendar.ZONE_OFFSET
import java.util.TimeZone.getTimeZone
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

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
        val tz =  getTimeZone(TIME_ZONE)
        return tz
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


    fun addLinks(textView: TextView, pattern: String, urlString: String) {
        Linkify.addLinks(textView, Pattern.compile(pattern), urlString, { s, start, end -> true }, { match, url -> "" })
    }

}
