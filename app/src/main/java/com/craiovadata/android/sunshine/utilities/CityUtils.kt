package com.craiovadata.android.sunshine.utilities

import android.content.Context
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DST_OFFSET
import java.util.Calendar.ZONE_OFFSET
import java.util.TimeZone.getTimeZone

object CityUtils {

    private const val TIME_ZONE =
      "US/Eastern"
//      "US/Central"
//      "US/Mountain"
//      "US/Pacific"

    private val images = intArrayOf(
        R.drawable.c,
        R.drawable.c2,
        R.drawable.stabil1,
        R.drawable.stabil2,
        R.drawable.stabil3,
        R.drawable.stabil4,
        R.drawable.stabil5,
        R.drawable.stabil6,
        R.drawable.stabil7
    )

    @JvmStatic
    fun getCityTimeZone(): TimeZone {
        val tz = getTimeZone(TIME_ZONE)
        if (BuildConfig.DEBUG) {
            require(tz.id != "GMT") { "timeZone probably wrong: GMT" }
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

    fun getBackResId(context: Context): Int {
//        if (BuildConfig.DEBUG) {
//            val imgNo = Random().nextInt(2)
//            return images[imgNo]
//        }
//        val now = System.currentTimeMillis()
//        val hoursSinceEpoch = TimeUnit.MILLISECONDS.toHours(now)
//        val n = (hoursSinceEpoch % images.size).toInt()

        val n = if (BuildConfig.DEBUG) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val storedResId = preferences.getInt("resId", 0)
            if (storedResId == 0) {
                preferences.edit().putInt("resId", 1).apply()
            } else {
                preferences.edit().putInt("resId", 0).apply()
            }
            storedResId
        } else Random().nextInt(images.size)

        return images[n]
    }


}
