package com.craiovadata.android.sunshine.utilities

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.main.MainActivity
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import org.json.JSONException
import org.json.JSONObject

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
                    Log.d(MainActivity.TAG, "timezoneAPI: $jsonObject")
                    val responseStatus = jsonObject.getString("status")
                    if (responseStatus == "OK") {
                        val timeZoneId = jsonObject.getString("timeZoneId")
                        if (timeZoneId == CityUtils.TIME_ZONE_ID) {
                            Log.e(MainActivity.TAG, "verification successful - timezone is OK")
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



     fun warnIfCityNameWrong(
         context: Context,
         currentWeatherEntry: WeatherEntry?,
         layoutAttention: LinearLayout
     ) {
        if (!BuildConfig.DEBUG) return
        if (currentWeatherEntry == null) return

        if (currentWeatherEntry.cityName.isEmpty()) return
        if (currentWeatherEntry.isCurrentWeather == 0) return  // only currentWeatherEntry contains cityName

        if (currentWeatherEntry.cityName != context.getString(R.string.app_name)) {  // ok
        //  !!! problem - wrong city name
            layoutAttention.visibility = View.VISIBLE
            val textToShow =
                "!!! orasul (primit de la OWM) se numeste: ${currentWeatherEntry.cityName}"
            layoutAttention.textViewWarnCityWrong.text = textToShow

            layoutAttention.buttonWarning.setOnClickListener {
                layoutAttention.visibility = View.GONE
            }
        }
    }

}