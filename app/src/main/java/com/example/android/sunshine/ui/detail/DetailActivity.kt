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
package com.example.android.sunshine.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.utilities.InjectorUtils
import java.util.*

/**
 * Displays single day's forecast
 */
class DetailActivity : AppCompatActivity() {
    lateinit var mViewModel: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
        val timestamp = intent.getLongExtra(WEATHER_ID_EXTRA, -1)
        //        Date _date = SunshineDateUtils.getNormalizedUtcDateForToday();
        val date = Date(timestamp)


        // Get the ViewModel from the factory
        val factory = InjectorUtils.provideDetailViewModelFactory(this.applicationContext, date)
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel::class.java)

        mViewModel.weather.observe(this, androidx.lifecycle.Observer { weatherEntry ->
            // Update the UI
//            if (list != null) bindWeatherToUI(list)
        })

        InjectorUtils.provideRepository(this).initializeData()

        if (BuildConfig.DEBUG){
            val prefs = getSharedPreferences("_", Context.MODE_PRIVATE)
            val txt = prefs.getString("txt", "")
//            textTest.text = txt
        }

    }

   /*

    private fun bindWeatherToUI(list: WeatherEntry) {
        /****************
         * Weather Icon *
         */

        val weatherId = list.weatherIconId
        //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
        val iconId = list.icon
        val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(iconId)

        /* Set the resource ID on the icon to display the art */
        weather_icon_detail.setImageResource(weatherImageId)

        /****************
         * Weather Date *
         */
        /*
         * The _date that is stored is a GMT representation at midnight of the _date when the weather
         * information was loaded for.
         *
         * When displaying this _date, one must add the GMT offset (in milliseconds) to acquire
         * the _date representation for the local _date in local time.
         * SunshineDateUtils#getFriendlyDateString takes care of this for us.
         */
        val localDateMidnightGmt = list._date!!.time
        val dateText = SunshineDateUtils.getFriendlyDateString(this@DetailActivity, localDateMidnightGmt, true)
        date_detail.text = dateText

        /***********************
         * Weather Description *
         */
        /* Use the weatherId to obtain the proper description */
        val description = SunshineWeatherUtils.getStringForWeatherCondition(this@DetailActivity, weatherId)

        /* Create the accessibility (a11y) String from the weather description */
        val descriptionA11y = getString(R.string.a11y_forecast, description)

        /* Set the text and content description (for accessibility purposes) */
        weather_description.text = description
        weather_description.contentDescription = descriptionA11y

        /* Set the content description on the weather image (for accessibility purposes) */
        weather_icon_detail.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         */

        val maxInCelsius = list.temp

        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        val highString = SunshineWeatherUtils.formatTemperature(this@DetailActivity, maxInCelsius)

        /* Create the accessibility (a11y) String from the weather description */
        val highA11y = getString(R.string.a11y_high_temp, highString)

        /* Set the text and content description (for accessibility purposes) */
        high_temperature_detail.text = highString
        high_temperature_detail.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         */

        //        double minInCelsius = list.getMin();
        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        //        String lowString = SunshineWeatherUtils.formatTemperature(DetailActivity.this, minInCelsius);

        //        String lowA11y = getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        //        mDetailBinding.primaryInfo.lowTemperature.setText(lowString);
        //        mDetailBinding.primaryInfo.lowTemperature.setContentDescription(lowA11y);

        /************
         * Humidity *
         */

        val humidity = list.humidity
        val humidityString = getString(R.string.format_humidity, humidity)
        val humidityA11y = getString(R.string.a11y_humidity, humidityString)

        /* Set the text and content description (for accessibility purposes) */
        humidity_measurement.text = humidityString
        humidity_measurement.contentDescription = humidityA11y

        humidity_label.contentDescription = humidityA11y

        /****************************
         * Wind speed and direction *
         */
        /* Read wind speed (in MPH) and direction (in compass degrees)*/
        val windSpeed = list.wind
        val windDirection = list.degrees
        val windString = SunshineWeatherUtils.getFormattedWind(this@DetailActivity, windSpeed, windDirection)
        val windA11y = getString(R.string.a11y_wind, windString)

        /* Set the text and content description (for accessibility purposes) */
        wind_measurement.text = windString
        wind_measurement.contentDescription = windA11y
        wind_label.contentDescription = windA11y

        /************
         * Pressure *
         */
        val pressure = list.pressure

        /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        val pressureString = getString(R.string.format_pressure, pressure)

        val pressureA11y = getString(R.string.a11y_pressure, pressureString)

        /* Set the text and content description (for accessibility purposes) */
        pressure_details.text = pressureString
        pressure_details.contentDescription = pressureA11y
        pressure_label.contentDescription = pressureA11y
    }
*/


    companion object {

        val WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA"
    }
}