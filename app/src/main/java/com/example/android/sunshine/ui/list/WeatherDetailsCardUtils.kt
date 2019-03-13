package com.example.android.sunshine.ui.list

import android.view.View
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.details_weather_card.view.*

object WeatherDetailsCardUtils {

    fun initWeatherDetailsCard(cardView: View, entry: WeatherEntry) {
        val context = cardView.context
        val temperature = entry.temp
        val dateInMillis = entry.date.time
        val iconId = entry.icon

        val highString = SunshineWeatherUtils.formatTemperature(context, temperature)
//        cardView.temperature_main.text = highString


        val dateString = SunshineDateUtils.getFriendlyDateString(context, dateInMillis, false)
//        cardView.weatherDate.text = dateString

        val weatherImageResourceId = SunshineWeatherUtils
                .getLargeArtResourceIdForIconCode(iconId)

//        cardView.weatherIcon.setImageResource(weatherImageResourceId)

        val weatherIconId = entry.weatherIconId
        val description = SunshineWeatherUtils.getStringForWeatherCondition(context, weatherIconId)
//        cardView.weatherDescription.text = description


//        cardView.setOnClickListener {
//            context.startActivity(Intent(context, DetailActivity::class.java))
//        }
    }


    fun bindWeatherToUI(cardView: View, weatherEntry: WeatherEntry) {
        /****************
         * Weather Icon *
         */
        val weatherId = weatherEntry.weatherIconId
        //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
        val iconId = weatherEntry.icon
        val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(iconId)

        /* Set the resource ID on the icon to display the art */
        cardView.weather_icon_detail.setImageResource(weatherImageId)

        /****************
         * Weather Date *
         */
        /*
         * The date that is stored is a GMT representation at midnight of the date when the weather
         * information was loaded for.
         *
         * When displaying this date, one must add the GMT offset (in milliseconds) to acquire
         * the date representation for the local date in local time.
         * SunshineDateUtils#getFriendlyDateString takes care of this for us.
         */
        val localDateMidnightGmt = weatherEntry.date!!.time
        val dateText = SunshineDateUtils.getFriendlyDateString(cardView.context, localDateMidnightGmt, true)
        cardView.date_detail.text = dateText

        /***********************
         * Weather Description *
         */
        /* Use the weatherId to obtain the proper description */
        val description = SunshineWeatherUtils.getStringForWeatherCondition(cardView.context, weatherId)

        /* Create the accessibility (a11y) String from the weather description */
        val descriptionA11y = cardView.context.getString(R.string.a11y_forecast, description)

        /* Set the text and content description (for accessibility purposes) */
        cardView.weather_description.text = description
        cardView.weather_description.contentDescription = descriptionA11y

        /* Set the content description on the weather image (for accessibility purposes) */
        cardView.weather_icon_detail.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         */

        val maxInCelsius = weatherEntry.temp

        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        val highString = SunshineWeatherUtils.formatTemperature(cardView.context, maxInCelsius)

        /* Create the accessibility (a11y) String from the weather description */
        val highA11y = cardView.context.getString(R.string.a11y_high_temp, highString)

        /* Set the text and content description (for accessibility purposes) */
        cardView.high_temperature_detail.text = highString
        cardView.high_temperature_detail.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         */

        //        double minInCelsius = weatherEntry.getMin();
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

        val humidity = weatherEntry.humidity
        val humidityString = cardView.context.getString(R.string.format_humidity, humidity)
        val humidityA11y = cardView.context.getString(R.string.a11y_humidity, humidityString)

      /* Set the text and content description (for accessibility purposes) */
        cardView.humidity_measurement.text = humidityString
        cardView.humidity_measurement.contentDescription = humidityA11y

        cardView.humidity_label.contentDescription = humidityA11y

        /****************************
         * Wind speed and direction *
         */
        val windSpeed = weatherEntry.wind
        val windDirection = weatherEntry.degrees
        val windString = SunshineWeatherUtils.getFormattedWind(cardView.context, windSpeed, windDirection)
        val windA11y = cardView.context.getString(R.string.a11y_wind, windString)

        cardView.wind_measurement.text = windString
        cardView.wind_measurement.contentDescription = windA11y
        cardView.wind_label.contentDescription = windA11y


        val pressure = weatherEntry.pressure

        /*
         * Format the pressure text using string resources. The reason we directly access
         * resources using getString rather than using a method from SunshineWeatherUtils as
         * we have for other data displayed in this Activity is because there is no
         * additional logic that needs to be considered in order to properly display the
         * pressure.
         */
        val pressureString = cardView.context.getString(R.string.format_pressure, pressure)

        val pressureA11y = cardView.context.getString(R.string.a11y_pressure, pressureString)

        /* Set the text and content description (for accessibility purposes) */
        cardView.pressure_details.text = pressureString
        cardView.pressure_details.contentDescription = pressureA11y
        cardView.pressure_label.contentDescription = pressureA11y


    }


}
