/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version c2.0 (the "License");
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

import android.content.Context
import android.net.Uri
import android.util.Log

import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.utilities.CityUtils

import java.io.IOException
import java.io.InputStream
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Scanner


/**
 * These utilities will be used to communicate with the weather servers.
 */
internal object NetworkUtils {

    private val TAG = NetworkUtils::class.java.simpleName
    private val BASE_OWM_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast?"
    private val BASE_OWM_WEATHER_NOW_URL = "http://api.openweathermap.org/data/2.5/weather?"
    private val OWM_USER_ID =
        "1dcb16570a0e21ac8c7938cc8c0c50c6" // OWM cont gratuit Miha Scobaru - creat 19 Sept 2019
    private val ID_PARAM = "id"
    private val APPID_PARAM = "APPID"

    /* The format we want our API to return */
    private val format = "json"
    /* The units we want our API to return */
    private val units = "metric"

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private val FORMAT_PARAM = "mode"
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private val UNITS_PARAM = "units"


    /**
     * Retrieves the proper URL to query for the weather data.
     *
     * @return URL to query weather service
     * @param mContext
     */
    fun getUrl(mContext: Context): URL? {
        val locationId = mContext.getString(R.string.owm_city_id)

        val loc2 = CityUtils.OWM_IDS.get(mContext.getString(R.string.app_name))
            ?: throw IllegalArgumentException("owm_id not found")
        return buildUrlWithLocationId(loc2.toString())
    }

    fun getUrlCurrentWeather(mContext: Context): URL? {
        val locationId = mContext.getString(R.string.owm_city_id)
        val loc2 = CityUtils.OWM_IDS.get(mContext.getString(R.string.app_name))
            ?: throw IllegalArgumentException("owm_id not found")

        return buildUrlWeatherNowWithLocationId(loc2.toString())
    }

    private fun buildUrlWithLocationId(locationID: String): URL? {
        val weatherQueryUri = Uri.parse(BASE_OWM_WEATHER_URL).buildUpon()
            .appendQueryParameter(ID_PARAM, locationID)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(APPID_PARAM, OWM_USER_ID)
            .build()

        try {
            val weatherQueryUrl = URL(weatherQueryUri.toString())
            Log.v(TAG, "URL forecasts 5 days 3 hours: $weatherQueryUrl")
            return weatherQueryUrl
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }

    }

    private fun buildUrlWeatherNowWithLocationId(locationID: String): URL? {
        val weatherQueryUri = Uri.parse(BASE_OWM_WEATHER_NOW_URL).buildUpon()
            .appendQueryParameter(ID_PARAM, locationID)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(APPID_PARAM, OWM_USER_ID)
            .build()

        try {
            val weatherQueryUrl = URL(weatherQueryUri.toString())
            Log.v(TAG, "URL current weather: $weatherQueryUrl")
            return weatherQueryUrl
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String? {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            var response: String? = null
            if (hasInput) {
                response = scanner.next()
            }
            scanner.close()
            return response
        } finally {
            urlConnection.disconnect()
        }
    }
}