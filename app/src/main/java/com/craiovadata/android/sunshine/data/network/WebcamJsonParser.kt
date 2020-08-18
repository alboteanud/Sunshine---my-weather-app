
package com.craiovadata.android.sunshine.data.network

import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry.Companion.CURRENT_WEATHER
import com.craiovadata.android.sunshine.ui.models.WebcamEntry
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.util.*

/**
 * Parser for OpenWeatherMap JSON data.
 */
internal class WebcamJsonParser {


    companion object {
        private const val STATUS = "status"
        private const val STATUS_OK = "OK"

        @Throws(JSONException::class)
        private fun hasHttpError(forecastJson: JSONObject): Boolean {
            if (forecastJson.has(STATUS) && forecastJson.getString(STATUS) == STATUS_OK ) return false
            return true
        }

        @Throws(JSONException::class)
        private fun fromJsonWebcams(webcamJson: JSONObject): Array<WebcamEntry> {
            val result = webcamJson.getJSONObject("result")
            val jsonWebcamArray = result.getJSONArray("webcams")
            val webcamEntries = mutableListOf<WebcamEntry>()

            for (i in 0 until jsonWebcamArray.length()) { // Get the JSON object representing the day
                val jsonWebcam = jsonWebcamArray.getJSONObject(i)
                val webcam = fromJsonWebcam(jsonWebcam)
//                weatherEntries[i] = weather
                webcamEntries.add(i, webcam)
            }
            return webcamEntries.toTypedArray()
        }

        @Throws(JSONException::class)
        private fun fromJsonWebcam(jsonWebcam: JSONObject ): WebcamEntry{
            val webcamId = jsonWebcam.getString("id")
            val statusActive = jsonWebcam.getString("status") == "active"
            val title = jsonWebcam.getString("title")
            val imageObj = jsonWebcam.getJSONObject("image")
            val updateMilli = imageObj.getLong("update") * 1000
            val previewUrl = imageObj.getJSONObject("current").getString("preview")

            return WebcamEntry(webcamId, Date(), statusActive, title, Date(updateMilli), previewUrl)
        }

        @Throws(JSONException::class)
        fun parseWebcamsResponse(jsonString: String?): WebcamResponse {
            if (jsonString == null) return WebcamResponse(emptyArray())
            val webcamJson = JSONObject(jsonString)
            // Is there an error?
            if (hasHttpError(webcamJson) ){
                return WebcamResponse(emptyArray())
            }
            val webcamsData = fromJsonWebcams(webcamJson)
            return WebcamResponse(webcamsData)
        }


    }
}