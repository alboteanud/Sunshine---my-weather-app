package com.example.android.sunshine.ui.recyclerViewTest

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.ui.cards.CurrentWeatherCardUtils
import com.example.android.sunshine.ui.cards.WeatherDetailsCardUtils

class CardsViewHolders {

    interface UpdateViewHolder {
        fun bindViews(update: Update)
    }

    class WeatherViewHolder(itemView: View, private val onWeatherImageClicked: Unit)
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

    class MapViewHolder(itemView: View, onMapClicked: Unit)
        : RecyclerView.ViewHolder(itemView), UpdateViewHolder {
        // get the views reference from itemView...
        override fun bindViews(update: Update) {
            val mapCard = update as MapUpdate

            // bind update values to views
        }
    }
}