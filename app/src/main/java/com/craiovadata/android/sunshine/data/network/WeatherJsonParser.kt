/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version city_2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craiovadata.android.sunshine.data.network

import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.WeatherEntry.Companion.CURRENT_WEATHER
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.util.*

/**
 * Parser for OpenWeatherMap JSON data.
 */
internal class WeatherJsonParser {
    @Throws(JSONException::class)
    fun parseForecastWeather(forecastJsonStr: String?): WeatherResponse {
        if (forecastJsonStr == null) return WeatherResponse(emptyArray())
        val forecastJson = JSONObject(forecastJsonStr)
        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return WeatherResponse(emptyArray())
        }
        val weatherForecast = fromJsonForecast(forecastJson)
        return WeatherResponse(weatherForecast)
    }
 @Throws(JSONException::class)
    fun parseForecastWeather2(forecastJsonStr: String?): WeatherResponse {
        if (forecastJsonStr == null) return WeatherResponse(emptyArray())
        val forecastJson = JSONObject(forecastJsonStr)
        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return WeatherResponse(emptyArray())
        }
        val weatherForecast = fromJsonForecast2(forecastJson)
        return WeatherResponse(weatherForecast)
    }

    @Throws(JSONException::class)
    fun parseCurrentWeather(forecastJsonStr: String?): WeatherResponse {
        if (forecastJsonStr == null) return WeatherResponse(emptyArray())
        val forecastJson = JSONObject(forecastJsonStr)
        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return WeatherResponse(emptyArray())
        }
        val weather = fromJsonCW(forecastJson)
        return WeatherResponse(arrayOf(weather))
    }

    companion object {
        // Weather information. Each day's forecast info is an element of the "list" array
        private const val OWM_LIST = "list"
        private const val OWM_MAIN = "main"
        private const val OWM_DAY_TIME = "dt"
        private const val OWM_WIND = "wind"
        private const val OWM_PRESSURE = "pressure"
        private const val OWM_HUMIDITY = "humidity"
        private const val OWM_WINDSPEED = "speed"
        private const val OWM_WIND_DIRECTION = "deg"
        // All temperatures are children of the "temperature" object
        private const val OWM_TEMP = "temp"
        private const val OWM_WEATHER = "weather"
        private const val OWM_WEATHER_ID = "id"
        private const val OWM_MESSAGE_CODE = "cod"
        private const val OWM_ICON = "icon"
        @Throws(JSONException::class)
        private fun hasHttpError(forecastJson: JSONObject): Boolean {
            if (forecastJson.has(OWM_MESSAGE_CODE)) {
                val errorCode =
                    forecastJson.getInt(OWM_MESSAGE_CODE)
                return when (errorCode) {
                    HttpURLConnection.HTTP_OK -> false
                    HttpURLConnection.HTTP_NOT_FOUND ->  // Server probably down
                        true
                    else -> true
                }
            }
            return false
        }

        // OWM 5days 3 hours
        @Throws(JSONException::class)
        private fun fromJsonForecast(forecastJson: JSONObject): Array<WeatherEntry> {
            val jsonWeatherArray =
                forecastJson.getJSONArray(OWM_LIST)
            val weatherEntries = mutableListOf<WeatherEntry>()
//                arrayOfNulls<WeatherEntry>(jsonWeatherArray.length())
            /*
         * OWM returns daily forecasts based upon the local time of the city that is being asked
         * for, which means that we need to know the GMT offset to translate this data properly.
         * Since this data is also sent in-order and the first day is always the current day, we're
         * going to take advantage of that to get a nice normalized UTC _date for all of our weather.
         */
//        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();
            val coordObj = forecastJson.getJSONObject("city").getJSONObject("coord")
            val lat = coordObj.getDouble("lat").toFloat()
            val lon = coordObj.getDouble("lon").toFloat()
            for (i in 0 until jsonWeatherArray.length()) { // Get the JSON object representing the day
                val dayForecast = jsonWeatherArray.getJSONObject(i)
                val weather =
                    fromJsonForecast(dayForecast, lat, lon)
//                weatherEntries[i] = weather
                weatherEntries.add(i, weather)
            }
            return weatherEntries.toTypedArray()
        }

        /*
* "list": [
{
"dt": 1577815200,
"main": {
"temp": 271.82,
"feels_like": 268.81,
"temp_min": 271.82,
"temp_max": 273.15,
"pressure": 1030,
"sea_level": 1030,
"grnd_level": 918,
"humidity": 64,
"temp_kf": -1.33
},
"weather": [
{
"id": 804,
"main": "Clouds",
"description": "Bedeckt",
"icon": "04n"
}
],
* */

 @Throws(JSONException::class)
        private fun fromJsonForecast2(forecastJson: JSONObject): Array<WeatherEntry> {
            val jsonWeatherArray =
                forecastJson.getJSONArray(OWM_LIST)
            val weatherEntries = mutableListOf<WeatherEntry>()
//                arrayOfNulls<WeatherEntry>(jsonWeatherArray.length())
            /*
         * OWM returns daily forecasts based upon the local time of the city that is being asked
         * for, which means that we need to know the GMT offset to translate this data properly.
         * Since this data is also sent in-order and the first day is always the current day, we're
         * going to take advantage of that to get a nice normalized UTC _date for all of our weather.
         */
//        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();
            val coordObj = forecastJson.getJSONObject("city").getJSONObject("coord")
            val lat = coordObj.getDouble("lat").toFloat()
            val lon = coordObj.getDouble("lon").toFloat()
            for (i in 0 until jsonWeatherArray.length()) { // Get the JSON object representing the day
                val dayForecast = jsonWeatherArray.getJSONObject(i)
                val weather =
                    fromJsonForecast2(dayForecast, lat, lon)
//                weatherEntries[i] = weather
                weatherEntries.add(i, weather)
            }
            return weatherEntries.toTypedArray()
        }

        // OWM 5days 3 hours
        @Throws(JSONException::class)
        private fun fromJsonForecast(
            dayForecast: JSONObject,
            lat: Float,
            lon: Float
        ): WeatherEntry { // We ignore all the datetime values embedded in the JSON and assume that
// the values are returned in-order by day (which is not guaranteed to be correct).
            val dateTimeMillis =
                dayForecast.getLong(OWM_DAY_TIME) * 1000
            val mainObject =
                dayForecast.getJSONObject(OWM_MAIN)
            val pressure =
                mainObject.getDouble(OWM_PRESSURE)
            val humidity = mainObject.getInt(OWM_HUMIDITY)
            val max = mainObject.getDouble(OWM_TEMP)
            val windObject =
                dayForecast.getJSONObject(OWM_WIND)
            val windSpeed =
                windObject.getDouble(OWM_WINDSPEED)
            val windDirection =
                windObject.getDouble(OWM_WIND_DIRECTION)
            // Description is in a child array called "weather", which is city_1 element long.
// That element also contains a weather code.
            val weatherObject =
                dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0)
            val weatherId = weatherObject.getInt(OWM_WEATHER_ID)
            val icon = weatherObject.getString(OWM_ICON)
            //        Log.d("TAG", iconCodeOWM);
// Create the weather entry object
            return WeatherEntry(
                weatherId,
                Date(dateTimeMillis),
                max,
                humidity.toDouble(),
                pressure,
                windSpeed,
                windDirection,
                icon,
                lat.toDouble(),
                lon.toDouble()
            )
        }

        // OWM 5days 3 hours
        @Throws(JSONException::class)
        private fun fromJsonForecast2(
            dayForecast: JSONObject,
            lat: Float,
            lon: Float
        ): WeatherEntry { // We ignore all the datetime values embedded in the JSON and assume that
            val weatherObject =
                dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0)
            val weatherId = weatherObject.getInt(OWM_WEATHER_ID)
            val description = weatherObject.getString("description")
            //        Log.d("TAG", iconCodeOWM);
// Create the weather entry object
            return WeatherEntry(
                weatherId,
                description
            )
        }

        // OWM current weather
//    private static WeatherEntry[] fromJsonCW(final JSONObject jsonCurrentWeather) throws JSONException {
        @Throws(JSONException::class)
        private fun fromJsonCW(jsonCurrentWeather: JSONObject): WeatherEntry {
            val mainObject =
                jsonCurrentWeather.getJSONObject(OWM_MAIN)
            val coordObj = jsonCurrentWeather.getJSONObject("coord")
            val lat = coordObj.getDouble("lat").toFloat()
            val lon = coordObj.getDouble("lon").toFloat()
            val pressure =
                mainObject.getDouble(OWM_PRESSURE)
            val humidity = mainObject.getInt(OWM_HUMIDITY)
            val temp = mainObject.getDouble(OWM_TEMP)
            val weatherArray =
                jsonCurrentWeather.getJSONArray(OWM_WEATHER)
            // Get the first weather obj - multiple objects for one location can arrive
            val weatherObj = weatherArray.getJSONObject(0)
            val weatherId = weatherObj.getInt(OWM_WEATHER_ID)
            val windObj =
                jsonCurrentWeather.getJSONObject(OWM_WIND)
            var windSpeed = 0.0 // m/s
            try {
                windSpeed = windObj.getDouble(OWM_WINDSPEED)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            var windDirection = 0.0
            try {
                windDirection = windObj.getDouble(OWM_WIND_DIRECTION)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val iconCode = weatherObj.getString(OWM_ICON)
            val dateTimeMillis =
                jsonCurrentWeather.getLong(OWM_DAY_TIME) * 1000
            val wDate = Date(dateTimeMillis)

            val sunrise = jsonCurrentWeather.getJSONObject("sys").getLong("sunrise")
            val sunset = jsonCurrentWeather.getJSONObject("sys").getLong("sunset")
            val dt = jsonCurrentWeather.getLong("dt")

            if (BuildConfig.DEBUG) {
                val cityName = jsonCurrentWeather.getString("name")
                return WeatherEntry(
                    weatherId,
                    wDate,
                    temp,
                    humidity.toDouble(),
                    pressure,
                    windSpeed,
                    windDirection,
                    iconCode,
                    CURRENT_WEATHER,
                    cityName,
                    lat.toDouble(),
                    lon.toDouble(),
                    sunrise, sunset, dt
                )
            }
             return WeatherEntry(
                weatherId,
                wDate,
                temp,
                humidity.toDouble(),
                pressure,
                windSpeed,
                windDirection,
                iconCode,
                CURRENT_WEATHER,
                lat.toDouble(),
                lon.toDouble(),
                 sunrise, sunset, dt
            )

//            constructor(
//                weatherId: Int, date: Date, temperature: Double, humidity: Double, pressure: Double,
//                wind: Double, degrees: Double, iconCodeOWM: String, isCurrentWeather: Int, lat: Double, lon: Double) {
//                this.weatherId = weatherId
//                this.date = date
//                this.temperature = temperature
//                this.humidity = humidity
//                this.pressure = pressure
//                this.wind = wind
//                this.degrees = degrees
//                this.iconCodeOWM = iconCodeOWM
//                this.isCurrentWeather = isCurrentWeather
//                this.lat = lat
//                this.lon = lon
//            }

        }
    }
}