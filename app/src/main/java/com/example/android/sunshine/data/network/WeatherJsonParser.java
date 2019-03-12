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
package com.example.android.sunshine.data.network;

import com.example.android.sunshine.data.database.WeatherEntry;
import com.example.android.sunshine.utilities.SunshineDateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Date;

import androidx.annotation.Nullable;

/**
 * Parser for OpenWeatherMap JSON data.
 */
final class WeatherJsonParser {

    // Weather information. Each day's forecast info is an element of the "list" array
    private static final String OWM_LIST = "list";
    private static final String OWM_MAIN = "main";
    private static final String OWM_DAY_TIME = "dt";
    private static final String OWM_WIND = "wind";

    private static final String OWM_PRESSURE = "pressure";
    private static final String OWM_HUMIDITY = "humidity";
    private static final String OWM_WINDSPEED = "speed";
    private static final String OWM_WIND_DIRECTION = "deg";

    // All temperatures are children of the "temp" object
    private static final String OWM_TEMP = "temp";

    // Max temperature for the day
    private static final String OWM_MAX = "temp_max";
    private static final String OWM_MIN = "temp_min";

    private static final String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_ID = "id";

    private static final String OWM_MESSAGE_CODE = "cod";
    private static final String OWM_ICON = "icon";

    private static boolean hasHttpError(JSONObject forecastJson) throws JSONException {
        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    return false;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    // Location invalid
                default:
                    // Server probably down
                    return true;
            }
        }
        return false;
    }


    // original - for testing server
    private static WeatherEntry[] fromJson_for_test_orig(final JSONObject forecastJson) throws JSONException {
        JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);

        WeatherEntry[] weatherEntries = new WeatherEntry[jsonWeatherArray.length()];

        /*
         * OWM returns daily forecasts based upon the local time of the city that is being asked
         * for, which means that we need to know the GMT offset to translate this data properly.
         * Since this data is also sent in-order and the first day is always the current day, we're
         * going to take advantage of that to get a nice normalized UTC date for all of our weather.
         */
        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();

        for (int i = 0; i < jsonWeatherArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject dayForecast = jsonWeatherArray.getJSONObject(i);

            // Create the weather entry object
//            WeatherEntry weather = fromJsonOWM(dayForecast);

            //  for fake server data format
            long dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i;
            WeatherEntry weather = fromJson_for_test_orig(dayForecast, dateTimeMillis);

            weatherEntries[i] = weather;
        }
        return weatherEntries;
    }

    // original - for testing server
    private static WeatherEntry fromJson_for_test_orig(final JSONObject dayForecast,
                                                       long dateTimeMillis) throws JSONException {
        // We ignore all the datetime values embedded in the JSON and assume that
        // the values are returned in-order by day (which is not guaranteed to be correct).

        double pressure = dayForecast.getDouble(OWM_PRESSURE);
        int humidity = dayForecast.getInt(OWM_HUMIDITY);
        double windSpeed = dayForecast.getDouble(OWM_WINDSPEED);
        double windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION);


        // Description is in a child array called "weather", which is c1 element long.
        // That element also contains a weather code.
        JSONObject weatherObject =
                dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);

        int weatherId = weatherObject.getInt(OWM_WEATHER_ID);


        //  Temperatures are sent by Open Weather Map in a child object called "temp".
        JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMP);
        double max = temperatureObject.getDouble("max");
        double min = temperatureObject.getDouble("min");

        // Create the weather entry object
        return new WeatherEntry(weatherId, new Date(dateTimeMillis), max,
                humidity, pressure, windSpeed, windDirection, "_" );
    }

    // OWM 5days c3 hours
    private static WeatherEntry[] fromJson5Days_(final JSONObject forecastJson) throws JSONException {
        JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);

        WeatherEntry[] weatherEntries = new WeatherEntry[jsonWeatherArray.length()];

        /*
         * OWM returns daily forecasts based upon the local time of the city that is being asked
         * for, which means that we need to know the GMT offset to translate this data properly.
         * Since this data is also sent in-order and the first day is always the current day, we're
         * going to take advantage of that to get a nice normalized UTC date for all of our weather.
         */
        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();

        for (int i = 0; i < jsonWeatherArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject dayForecast = jsonWeatherArray.getJSONObject(i);
            WeatherEntry weather = fromJson5Days(dayForecast);

            weatherEntries[i] = weather;
        }
        return weatherEntries;
    }

    // OWM 5days c3 hours
    private static WeatherEntry fromJson5Days(final JSONObject dayForecast) throws JSONException {
        // We ignore all the datetime values embedded in the JSON and assume that
        // the values are returned in-order by day (which is not guaranteed to be correct).

        long dateTimeMillis = dayForecast.getLong(OWM_DAY_TIME) * 1000;

        JSONObject mainObject = dayForecast.getJSONObject(OWM_MAIN);
        double pressure = mainObject.getDouble(OWM_PRESSURE);
        int humidity = mainObject.getInt(OWM_HUMIDITY);
        double max = mainObject.getDouble(OWM_TEMP);

        JSONObject windObject = dayForecast.getJSONObject(OWM_WIND);
        double windSpeed = windObject.getDouble(OWM_WINDSPEED);
        double windDirection = windObject.getDouble(OWM_WIND_DIRECTION);


        // Description is in a child array called "weather", which is c1 element long.
        // That element also contains a weather code.
        JSONObject weatherObject =
                dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);

        int weatherId = weatherObject.getInt(OWM_WEATHER_ID);
        String icon = weatherObject.getString(OWM_ICON);
//        Log.d("TAG", icon);

        // Create the weather entry object
        return new WeatherEntry(weatherId, new Date(dateTimeMillis), max,
                humidity, pressure, windSpeed, windDirection, icon);
    }

    // OWM current weather
    private static WeatherEntry[] fromJsonNow(final JSONObject weatherNow) throws JSONException {
        WeatherEntry weather = fromJsonNow_(weatherNow);
        return new WeatherEntry[]{weather};
    }

    // OWM current weather
    private static WeatherEntry fromJsonNow_(final JSONObject jsonObject) throws JSONException {

        JSONObject mainObject = jsonObject.getJSONObject(OWM_MAIN);
        double pressure = mainObject.getDouble(OWM_PRESSURE);
        int humidity = mainObject.getInt(OWM_HUMIDITY);
        double temp = mainObject.getDouble(OWM_TEMP);
//        temp = - temp;  // for debug

        JSONArray weatherArray = jsonObject.getJSONArray(OWM_WEATHER);
        // Get the first weather obj - multiple objects for one location can arrive
        JSONObject weatherObj = weatherArray.getJSONObject(0);
        int weatherId = weatherObj.getInt(OWM_WEATHER_ID);

        JSONObject windObj = jsonObject.getJSONObject(OWM_WIND);
        double windSpeed = 0;
        try {
            windObj.getDouble(OWM_WINDSPEED);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        double windDirection = 0;
        try {
            windDirection = windObj.getDouble(OWM_WIND_DIRECTION);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String iconCode = weatherObj.getString(OWM_ICON);

        long dateTimeMillis = jsonObject.getLong(OWM_DAY_TIME) * 1000;
        Date wDate = new Date(dateTimeMillis);

        // Create the weather entry object
        return new WeatherEntry(weatherId, wDate, temp,
                humidity, pressure, windSpeed, windDirection, iconCode, 1);
    }


    /**
     * for testing sever
     */
    @Nullable
    WeatherResponse parse_for_test_orig(final String forecastJsonStr) throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return null;
        }

        WeatherEntry[] weatherForecast = fromJson_for_test_orig(forecastJson);

        return new WeatherResponse(weatherForecast);
    }


    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     *
     * @param forecastJsonStr JSON response from server
     * @return Array of Strings describing weather data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    @Nullable
    WeatherResponse parse(final String forecastJsonStr) throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return null;
        }

        WeatherEntry[] weatherForecast = fromJson5Days_(forecastJson);

        return new WeatherResponse(weatherForecast);
    }


    @Nullable
    WeatherResponse parseCurrentWeather(final String forecastJsonStr) throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return null;
        }

        WeatherEntry[] weatherForecast = fromJsonNow(forecastJson);

        return new WeatherResponse(weatherForecast);
    }


}