package com.example.android.sunshine.ui.recyclerViewTest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.R
import com.example.android.sunshine.ui.list.MainActivity

class CardsAdapter(val context: Context,
                   var listEntry: List<Update>,
                   val listener: onItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    private var listEntry: List<Update>? = null

    companion object {
        const val VIEW_TYPE_WEATHER = 0
        const val VIEW_TYPE_GRAPH = 1
        const val VIEW_TYPE_DETAILS = 2
        const val VIEW_TYPE_MAP = 3
    }

    override fun getItemViewType(position: Int): Int {
        val type = when (listEntry.get(position)._type) {
            Update.TYPE.WEATHER -> VIEW_TYPE_WEATHER
            Update.TYPE.DETAILS -> VIEW_TYPE_DETAILS
            Update.TYPE.GRAPH -> VIEW_TYPE_GRAPH
            Update.TYPE.MAP -> VIEW_TYPE_MAP
            // other types...
            else -> VIEW_TYPE_WEATHER
        }
        return type
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {

            VIEW_TYPE_WEATHER -> CardsViewHolders.WeatherViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.weather_card, viewGroup, false),
                    listener.onWeatherImageClicked())

            VIEW_TYPE_DETAILS -> CardsViewHolders.DetailsViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.details_weather_card, viewGroup, false))

            VIEW_TYPE_MAP -> CardsViewHolders.MapViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.map_card, viewGroup, false),
                    listener.onMapClicked())

            else -> CardsViewHolders.WeatherViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.weather_card, viewGroup, false),
                    listener.onWeatherImageClicked())
        }
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardsViewHolders.UpdateViewHolder).bindViews(listEntry.get(position))
    }

    override fun getItemCount() = listEntry.size

    fun setUpdates(updates: List<Update>) {
        listEntry = updates
        notifyDataSetChanged()
    }

    interface onItemClickListener {
        fun onWeatherImageClicked()
        fun onMapClicked()

        // other listeners...
    }

    /**
     * Swaps the list used by the ForecastAdapter for its weather data. This method is called by
     * [MainActivity] after a load has finished. When this method is called, we assume we have
     * a new set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newForecast the new list of forecasts to use as ForecastAdapter's data source
     */
    fun swapForecast(newForecast: List<Update>) {
        // If there was no forecast data, then recreate all of the list
//       if (mForecast == null)  {
            listEntry = newForecast
            notifyDataSetChanged()


//        }
//       else {
////+            * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
////+            * shows the four methods you need to override to return a DiffUtil callback. The
////+            * old list is the current list stored in listEntry, where the new list is the new
////+            * values passed in from the observing the database.
//
//            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//
//                override fun getNewListSize(): Int {
//                    return newForecast.size
//                }
//
//                override fun getOldListSize(): Int {
//                    return listEntry.size ?: 0
//                }
//
//               override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                    return listEntry.get(oldItemPosition)?._id == newForecast[newItemPosition]._id
//                }
//
//                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                    val newWeather = newForecast[newItemPosition]
//                    val oldWeather = listEntry?.get(oldItemPosition)
//                    return newWeather._id == oldWeather?._id && newWeather._date == oldWeather._date
//                }
//            })
//            listEntry = newForecast
//            result.dispatchUpdatesTo(this)
//        }



    }
}