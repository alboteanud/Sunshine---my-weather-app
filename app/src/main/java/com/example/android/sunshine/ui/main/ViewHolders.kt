package com.example.android.sunshine.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.ui.cards.*
import com.example.android.sunshine.ui.update_models.*


class ViewHolders {

    interface UpdateViewHolder {
        fun bindViews(update: BaseUpdate)
    }

    class WeatherViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: BaseUpdate) {
            val currentWeatherUpdate = update as CurrentWeatherUpdate
            val entry = currentWeatherUpdate.weatherEntry
            CurrentWeatherCardUtils.bindWeatherToUI(entry, itemView)
        }
    }

    class DetailsViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: BaseUpdate) {
            val detailsUpdate = update as DetailsUpdate
            val entry = detailsUpdate.weatherEntry
            WeatherDetailsCardUtils.bindWeatherToUI(entry, itemView)

            // bind update values to views
        }
    }

    class GraphViewHolder(itemView: View, private val listener: Adapter.onItemClickListener)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: BaseUpdate) {
            val forecastUpdate = update as GraphForecastUpdate
            val list = forecastUpdate.list
            GraphCardUtils.bindForecastToUI(list, itemView, listener)

            // bind update values to views
        }
    }

    class MapViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: BaseUpdate) {
            val mapUpdate = update as MapUpdate
            val entry = mapUpdate.weatherEntry
            MapCardUtils.bindMapToUI(entry, itemView)
        }
    }

    class AdsViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: BaseUpdate) {
            val adsUpdate = update as AdBannerUpdate
            val adView = adsUpdate.adView
            AdsCardUtils.bindAdsToUI(itemView, adView)
            // bind update values to views
        }
    }


}