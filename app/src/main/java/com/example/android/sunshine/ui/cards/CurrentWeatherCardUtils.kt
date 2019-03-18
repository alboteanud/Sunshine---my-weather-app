package com.example.android.sunshine.ui.cards
import android.view.View
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import kotlinx.android.synthetic.main.current_weather_card.view.*

object CurrentWeatherCardUtils {

    fun bindWeatherToUI(entry: WeatherEntry, cardView: View) {
        /****************
         * Weather Icon *
         */
        val weatherId = entry.weatherIconId
        //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
        val iconId = entry.icon
        val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(iconId)

        /* Set the resource ID on the icon to display the art */
        cardView.weatherIcon.setImageResource(weatherImageId)

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
//        val localDateMidnightGmt = entry.date.time
//        val dateText = SunshineDateUtils.getFriendlyDateString(cardView.context, localDateMidnightGmt, true)
//        cardView.weatherDate.text = dateText

        /***********************
         * Weather Description *
         */
        /* Use the weatherId to obtain the proper description */
        val description = SunshineWeatherUtils.getStringForWeatherCondition(cardView.context, weatherId)

        /* Create the accessibility (a11y) String from the weather description */
        val descriptionA11y = cardView.context.getString(R.string.a11y_forecast, description)

        /* Set the text and content description (for accessibility purposes) */
        cardView.weatherDescription.text = description
        cardView.weatherDescription.contentDescription = descriptionA11y

        /* Set the content description on the weather image (for accessibility purposes) */
        cardView.weatherIcon.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         */

        val maxInCelsius = entry.temp

        /*
         * If the user's preference for weather is fahrenheit, formatTemperature will convert
         * the temperature. This method will also append either °C or °F to the temperature
         * String.
         */
        val highString = SunshineWeatherUtils.formatTemperature(cardView.context, maxInCelsius)

        /* Create the accessibility (a11y) String from the weather description */
        val highA11y = cardView.context.getString(R.string.a11y_high_temp, highString)

        /* Set the text and content description (for accessibility purposes) */
        cardView.temperatureText.text = highString
        cardView.temperatureText.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         */

        //        double minInCelsius = entry.getMin();
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






//        val temperature = entry.temp
//        val dateInMillis = entry.date.time
//        val iconId = entry.icon
//
//        val highString = SunshineWeatherUtils.formatTemperature(card.context, temperature)
//        card.temperature_main?.text = highString
//
//        val dateString = SunshineDateUtils.getFriendlyDateString(card.context, dateInMillis, false)
//        card.weatherDate?.text = dateString
//
//        val weatherImageResourceId = SunshineWeatherUtils
//                .getLargeArtResourceIdForIconCode(iconId)
//
//        card.weatherIcon?.setImageResource(weatherImageResourceId)
//
//        val weatherIconId = entry.weatherIconId
//        val description = SunshineWeatherUtils.getStringForWeatherCondition(card.context, weatherIconId)
//        card.weatherDescription?.text = description

//        itemView.weatherIcon.setOnClickListener { onWeatherImageClicked }

    }
}
