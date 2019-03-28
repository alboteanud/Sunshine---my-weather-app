package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.Base

class CardsAdapter(val context: Context,
                   var listUpdates: List<Base>,
                   private val listener: Listener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_WEATHER = 0
        const val VIEW_TYPE_GRAPH = 1
        const val VIEW_TYPE_DETAILS = 2
        const val VIEW_TYPE_MAP = 3
        const val VIEW_TYPE_ADS = 4
        const val VIEW_TYPE_DAYS = 5
    }

    override fun getItemViewType(position: Int): Int {
        return when (listUpdates[position]._type) {
            Base.TYPE.WEATHER -> VIEW_TYPE_WEATHER
            Base.TYPE.DETAILS -> VIEW_TYPE_DETAILS
            Base.TYPE.GRAPH -> VIEW_TYPE_GRAPH
            Base.TYPE.MAP -> VIEW_TYPE_MAP
            Base.TYPE.DAYS -> VIEW_TYPE_DAYS
            else -> VIEW_TYPE_ADS
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_WEATHER -> CardsViewHolders.WeatherViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.current_weather_card, viewGroup, false))

            VIEW_TYPE_GRAPH -> CardsViewHolders.GraphViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.graph_card, viewGroup, false),
                    listener)

            VIEW_TYPE_DETAILS -> CardsViewHolders.DetailsViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.details_weather_card, viewGroup, false))

            VIEW_TYPE_MAP -> CardsViewHolders.MapViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.map_card, viewGroup, false))

            VIEW_TYPE_DAYS -> CardsViewHolders.MultyDayViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.multi_day_card, viewGroup, false))

            else -> CardsViewHolders.AdsViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.ads_card, viewGroup, false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardsViewHolders.UpdateViewHolder).bindViews(listUpdates[position])
    }

    override fun getItemCount() = listUpdates.size

    interface Listener {
        //        fun onWeatherImageClicked()
        fun onCelsiusFarClicked(view: View)
//        fun onMapClicked()
    }

    /**
     * Swaps the list used by the ForecastAdapter for its weather data. This method is called by
     * [MainActivity] after a load has finished. When this method is called, we assume we have
     * a new set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newUpdates the new list of forecasts to use as ForecastAdapter's data source
     */
    fun setUpdates(newUpdates: List<Base>) {
        // If there was no forecast data, then recreate all of the list
        if (listUpdates.isEmpty()) {
            listUpdates = newUpdates
            notifyDataSetChanged()

        } else {
//+            * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
//+            * shows the four methods you need to override to return a DiffUtil callback. The
//+            * old list is the current list stored in listUpdates, where the new list is the new
//+            * values passed in from the observing the database.

            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getNewListSize(): Int {
                    return newUpdates.size
                }

                override fun getOldListSize(): Int {
                    return listUpdates.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return listUpdates[oldItemPosition]._id == newUpdates[newItemPosition]._id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newWeather = newUpdates[newItemPosition]
                    val oldWeather = listUpdates[oldItemPosition]
                    return newWeather._id == oldWeather._id && newWeather._date == oldWeather._date
                }
            })
            listUpdates = newUpdates
            result.dispatchUpdatesTo(this)
        }


    }
}