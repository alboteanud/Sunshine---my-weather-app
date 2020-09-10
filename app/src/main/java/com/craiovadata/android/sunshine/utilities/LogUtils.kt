package com.craiovadata.android.sunshine.utilities

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.main.MainActivity
import kotlinx.android.synthetic.main.content_main.view.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

object LogUtils {

    @JvmStatic
    fun logEntries(context: Context, entries: List<WeatherEntry>) {
        if (!BuildConfig.DEBUG) return
        val simpleDateFormat = CityData.getFormatterCityTZ("HH:mm  dd MMM")

        entries.forEachIndexed { i, entry ->
            val date = simpleDateFormat.format(entry.date.time)
            val temperature = SunshineWeatherUtils.formatTemperature(context, entry.temperature)
            log("entry[$i] $date  $temperature isCW-${entry.isCurrentWeather} " +
                        "id-${entry.date} "
            )
        }
    }

    fun checkIfTimezoneWrong(
        context: Context,
        currentWeatherEntry: WeatherEntry?,
        layoutAttention: LinearLayout
    ) {
        if (!BuildConfig.DEBUG) return
        if (currentWeatherEntry == null) return

        val timsetampSec = System.currentTimeMillis() / 1000

        val url = "https://maps.googleapis.com/maps/api/timezone/json?" +
                "location=${currentWeatherEntry.lat},${currentWeatherEntry.lon}" +
                "&timestamp=${timsetampSec}" +
                "&key=${context.getString(R.string.GOOGLE_API_KEY)}"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                var warningText: String? = null
                try {
                    val jsonObject = JSONObject(response)
                    log("timezoneAPI: $jsonObject")
                    val responseStatus = jsonObject.getString("status")
                    if (responseStatus == "OK") {
                        val timeZoneId = jsonObject.getString("timeZoneId")
                        if (timeZoneId == CityData.TIME_ZONE_ID) {
                            log("verification successful - timezone is OK")
                        } else {
                            warningText =
                                "error timezoneId should be: $timeZoneId"
                        }
                    } else {
                        warningText = "timezoneAPI -> status: $responseStatus"
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    warningText = "timezoneAPI -> JSONException while parsing the response"
                }

                if (warningText != null) {
                    layoutAttention.visibility = View.VISIBLE
                    layoutAttention.textViewWarnTimezone.text = warningText
                    layoutAttention.buttonWarning.setOnClickListener {
                        layoutAttention.visibility = View.GONE
                    }
                }
            },
            Response.ErrorListener {
                layoutAttention.visibility = View.VISIBLE
                val warningText = "timezoneAPI: volley error"
                layoutAttention.textViewWarnTimezone.text = warningText
                layoutAttention.buttonWarning.setOnClickListener {
                    layoutAttention.visibility = View.GONE
                }
            })

        val queue = Volley.newRequestQueue(context)
        queue.add(stringRequest)
    }


    fun getAdIdPetru(context: Context): String {
        if (System.currentTimeMillis() < 1616510552000L          // 3 2021
            || Random().nextInt(4) == 1
        )
            return context.getString(R.string.admob_banner_id)  // Petru
        return "ca-app-pub-3931793949981809/6280930567"  // ad

    }

    fun addTestText(context: Context, text: String) {
        if (!inTestMode) return
        val pref = context.getSharedPreferences("_", MODE_PRIVATE)

        var savedTxt = pref.getString(MainActivity.PREF_SYNC_KEY, "")
        val format = SimpleDateFormat("HH.mm")
        savedTxt += " $text" + "_" + format.format(System.currentTimeMillis())
        pref.edit().putString(MainActivity.PREF_SYNC_KEY, savedTxt).apply()
    }

    fun log(msg: String) {
        if (!inTestMode) return
        Log.d("log Dan", msg)
    }


}