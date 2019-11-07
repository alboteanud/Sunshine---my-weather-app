package com.craiovadata.android.sunshine.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.craiovadata.android.sunshine.utilities.AppExecutors
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.main.MainActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils.provideRepository
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils

class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        val repository = provideRepository(context)
        AppExecutors.instance.diskIO().execute {

            val currentWeather = repository.currentWeatherList

            if (currentWeather != null && currentWeather.size > 0) {

                val currentWeatherEntry = currentWeather[0]
                updateWidgets(context, appWidgetManager, appWidgetIds, currentWeatherEntry)
//                            logDBvalues(context, mutableListOf(), currentWeather)
            } else {
                //            showLoading()
            }

        }


    }

    private fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray, currentWeather: WeatherEntry) {

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (appWidgetId in appWidgetIds) {
            // Create an Intent to launch ExampleActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views = RemoteViews(context.packageName, R.layout.my_widget_layout)
            views.setOnClickPendingIntent(R.id.widgetView, pendingIntent)

            /****************
             * Weather Icon *
             */
            val weatherId = currentWeather.weatherId
            //        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
                  val weatherDescription = SunshineWeatherUtils.getStringForWeatherCondition(context, weatherId)
            val iconId = currentWeather.iconCodeOWM
//            val iconId = "01n"
            val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForIconCode(iconId)
            val weatherIconDescription = context.getString(R.string.a11y_forecast_icon, weatherDescription)
            /* Set the resource ID on the iconCodeOWM to display the art */
            views.setImageViewResource(R.id.widget_icon_weather, weatherImageId)
            views.setContentDescription(R.id.widget_icon_weather, weatherIconDescription)




            /**************************
             * High (max) temperature *
             */

            val tempTxt = SunshineWeatherUtils.formatTemperature(context, currentWeather.temperature)
             val tempDescr = context.getString(R.string.a11y_high_temp, tempTxt)

            views.setContentDescription(R.id.widgetTextViewTemperature, tempDescr)
            views.setTextViewText(R.id.widgetTextViewTemperature, tempTxt)


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
