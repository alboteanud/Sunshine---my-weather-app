package com.craiovadata.android.sunshine.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.ui.adpterModels.*
import com.craiovadata.android.sunshine.ui.adpterModels.Map


class CardsViewHolders {

    interface UpdateViewHolder {
        fun bindViews(update: Base)
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val currentWeatherUpdate = update as CurrentWeather
            val entry = currentWeatherUpdate.weatherEntry
            CurrentWeather.bindWeatherToUI(entry, itemView)
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val detailsUpdate = update as Details
            val entry = detailsUpdate.weatherEntry
            Details.bindWeatherToUI(entry, itemView)

            // bind update values to views
        }
    }

    class GraphViewHolder(itemView: View, private val listener: CardsAdapter.Listener) :
        RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val forecastUpdate = update as Graph
            val list = forecastUpdate.list
            Graph.bindForecastToUI(list, itemView, listener)

            // bind update values to views
        }
    }

    class MultyDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val multiDayUpdate = update as MultiDay

            MultiDay.bindForecastToUI(multiDayUpdate.list, itemView)

            // bind update values to views
        }
    }

    class MapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val mapUpdate = update as Map
            val entry = mapUpdate.weatherEntry
//            if (!BuildConfig.DEBUG)
                Map.bindMapToUI(entry, itemView)
        }
    }
  class WebcamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        override fun bindViews(update: Base) {
            val webcamUpdate = update as Webcam
            Webcam.bindPhotoToUI(webcamUpdate.webcams, itemView)
        }
    }

    class NewsViewHolder(itemView: View, private val listener: CardsAdapter.Listener) :
        RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
//            val newsUpdate = update as com.craiovadata.android.sunshine.ui.adpterModels.News

            News.bindNewsToUI(itemView, listener)
        }
    }

    class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Base) {
            val adsUpdate = update as Ads
            val adView = adsUpdate.adView
            Ads.bindAdsToUI(itemView, adView)
            // bind update values to views
        }
    }


}