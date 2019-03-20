package com.example.android.sunshine.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.R
import com.example.android.sunshine.ui.update_models.BaseUpdate

class Adapter(val context: Context,
              var listUpdates: List<BaseUpdate>,
              val listener: onItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_WEATHER = 0
        const val VIEW_TYPE_GRAPH = 1
        const val VIEW_TYPE_DETAILS = 2
        const val VIEW_TYPE_MAP = 3
        const val VIEW_TYPE_ADS = 4
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (listUpdates[position]._type) {
            BaseUpdate.TYPE.WEATHER -> VIEW_TYPE_WEATHER
            BaseUpdate.TYPE.DETAILS -> VIEW_TYPE_DETAILS
            BaseUpdate.TYPE.GRAPH -> VIEW_TYPE_GRAPH
            BaseUpdate.TYPE.MAP -> VIEW_TYPE_MAP
//            BaseUpdate.TYPE.ADS -> VIEW_TYPE_ADS
            else -> VIEW_TYPE_ADS
        }
        return type
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            VIEW_TYPE_WEATHER -> ViewHolders.WeatherViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.current_weather_card, viewGroup, false))

            VIEW_TYPE_GRAPH -> ViewHolders.GraphViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.graph_card, viewGroup, false),
                    listener)

            VIEW_TYPE_DETAILS -> ViewHolders.DetailsViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.details_weather_card, viewGroup, false))

            VIEW_TYPE_MAP -> ViewHolders.MapViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.map_card, viewGroup, false))

            else -> ViewHolders.AdsViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.ads_card, viewGroup, false))
        }
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolders.UpdateViewHolder).bindViews(listUpdates.get(position))
    }

    override fun getItemCount() = listUpdates.size

    interface onItemClickListener {
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
    fun setUpdates(newUpdates: List<BaseUpdate>) {
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
                    return listUpdates.get(oldItemPosition)._id == newUpdates[newItemPosition]._id
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