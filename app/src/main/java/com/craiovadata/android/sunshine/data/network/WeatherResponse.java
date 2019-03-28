package com.craiovadata.android.sunshine.data.network;

import com.craiovadata.android.sunshine.data.database.WeatherEntry;

import androidx.annotation.NonNull;

/**
 * Weather response from the backend. Contains the weather forecasts.
 */
class WeatherResponse {

    @NonNull
    private final WeatherEntry[] mWeatherForecast;

    public WeatherResponse(@NonNull final WeatherEntry[] weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

    public WeatherEntry[] getWeatherForecast() {
        return mWeatherForecast;
    }
}