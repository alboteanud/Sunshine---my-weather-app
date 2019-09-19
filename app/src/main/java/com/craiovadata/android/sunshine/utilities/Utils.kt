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
            R.drawable.stabil1,
            R.drawable.stabil2,
            R.drawable.stabil3,
            R.drawable.stabil4,
            R.drawable.stabil5,
            R.drawable.stabil6
    )

    @JvmStatic
    fun getCityTimeZone(): TimeZone {
        val tz = getTimeZone(TIME_ZONE)
        if(BuildConfig.DEBUG){
            if (tz.id == "GMT"){
                throw IllegalArgumentException("timeZone probably wrong: GMT")
            }
        }
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
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
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


    fun addLinks(textView: TextView, pattern: String, urlString: String) {
        Linkify.addLinks(textView, Pattern.compile(pattern), urlString, { _, _, _ -> true }, { _, _ -> "" })
    }


}
