package com.example.android.sunshine.ui.recyclerViewTest

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.ui.cards.*


class ViewHolders {

    interface UpdateViewHolder {
        fun bindViews(update: Update)
    }

    class WeatherViewHolder(itemView: View, private val onImgClicked: Unit)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val currentWeatherUpdate = update as CurrentWeatherUpdate
            val entry = currentWeatherUpdate.weatherEntry
            CurrentWeatherCardUtils.bindWeatherToUI(entry, itemView)
        }
    }

    class DetailsViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val detailsUpdate = update as DetailsUpdate
            val entry = detailsUpdate.weatherEntry
            WeatherDetailsCardUtils.bindWeatherToUI(entry, itemView)

            // bind update values to views
        }
    }

    class GraphViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val forecastUpdate = update as GraphForecastUpdate
            val list = forecastUpdate.list
            TemperatureGraphCardUtils.bindForecastToUI(list, itemView)

            // bind update values to views
        }
    }

    class MapViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val mapUpdate = update as MapUpdate
            val entry = mapUpdate.weatherEntry
            MapCardUtils.bindMapToUI(entry.lat, entry.lon, itemView)
        }
    }

    class AdsViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val adsUpdate = update as AdsUpdate
            val adView = adsUpdate.adView
            AdsCardUtils.bindAdsToUI(itemView, adView)
            // bind update values to views
        }
    }


}