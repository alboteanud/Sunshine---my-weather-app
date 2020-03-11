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

import android.content.Context
import android.net.Uri
import android.util.Log

import com.craiovadata.android.sunshine.R

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Scanner

/**
 * These utilities will be used to communicate with the weather servers.
 */
internal object NetworkUtils {

    private const val TAG = "NetworkUtils"
    private const val BASE_OWM_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast?"
    private const val BASE_OWM_WEATHER_NOW_URL = "http://api.openweathermap.org/data/2.5/weather?"

    private const val ID_PARAM = "id"
    private const val APPID_PARAM = "APPID"

    /* The format we want our API to return */
    private const val format = "json"
    /* The units we want our API to return */
    private const val units = "metric"

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private const val FORMAT_PARAM = "mode"
    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private const val UNITS_PARAM = "units"

    fun getUrl(mContext: Context): URL {
        val owmApiKey = mContext.getString(R.string.owm_api_key)
        val owmCityId = mContext.getString(R.string.owm_city_id)
        return buildUrlWithLocationId(owmCityId, owmApiKey)
    }

    fun getUrl2(mContext: Context, cityId: Int, language: String): URL? {
        val owmApiKey = mContext.getString(R.string.owm_api_key)
        return buildUrlWithLocationId2(cityId.toString(), owmApiKey, language )
    }

    fun getUrlCurrentWeather(mContext: Context): URL? {
        val owmApiKey = mContext.getString(R.string.owm_api_key)
        val owmCityId = mContext.getString(R.string.owm_city_id)
        return buildUrlWeatherNowWithLocationId(owmCityId, owmApiKey)
    }

    private fun buildUrlWithLocationId(locationID: String, owmApiKey: String): URL {
        val weatherQueryUri = Uri.parse(BASE_OWM_WEATHER_URL).buildUpon()
            .appendQueryParameter(ID_PARAM, locationID)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(APPID_PARAM, owmApiKey)
            .build()
        val weatherQueryUrl = URL(weatherQueryUri.toString())
        Log.v(TAG, "URL forecasts 5 days = $weatherQueryUrl")
        return weatherQueryUrl

    }

    private fun buildUrlWithLocationId2(locationID: String, owmApiKey: String, language: String): URL? {
        val weatherQueryUri = Uri.parse(BASE_OWM_WEATHER_URL).buildUpon()
            .appendQueryParameter(ID_PARAM, locationID)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(APPID_PARAM, owmApiKey)
            .appendQueryParameter("lang", language)
            .build()

        return try {
            val weatherQueryUrl = URL(weatherQueryUri.toString())
            Log.v(TAG, "URL forecasts 5 days = $weatherQueryUrl")
            weatherQueryUrl
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
        }
    }


    private fun buildUrlWeatherNowWithLocationId(
        locationID: String,
        owmApiKey: String
    ): URL? {
        val weatherQueryUri = Uri.parse(BASE_OWM_WEATHER_NOW_URL).buildUpon()
            .appendQueryParameter(ID_PARAM, locationID)
            .appendQueryParameter(FORMAT_PARAM, format)
            .appendQueryParameter(UNITS_PARAM, units)
            .appendQueryParameter(APPID_PARAM, owmApiKey)
            .build()

        return try {
            val weatherQueryUrl = URL(weatherQueryUri.toString())
            Log.v(TAG, "URL current weather: $weatherQueryUrl")
            weatherQueryUrl
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            null
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