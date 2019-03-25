package com.example.android.sunshine.ui.models

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import kotlinx.android.synthetic.main.map_card.view.*
import java.util.*

data class Map(val weatherEntry: WeatherEntry?)
    : Base(-4, Base.TYPE.MAP, Date(0)) {

    companion object {

        @JvmStatic
        fun bindMapToUI(weatherEntry: WeatherEntry?, itemView: View) {
            if (weatherEntry == null) return

            val url = buildUrlGoogleStaticMap(itemView.context, weatherEntry)
            Glide.with(itemView.mapImageView)
                    .load(url)
                    .into(itemView.mapImageView)
        }

        private const val BASE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?"

        private fun buildUrlGoogleStaticMap(context: Context, weatherEntry: WeatherEntry): String {
            val apiKey = context.getString(R.string.STATIC_MAP_KEY)
            val scale = context.resources.getInteger(R.integer.scale_static_map).toString()
            val staticMapUri = Uri.parse(BASE_STATIC_MAP_URL).buildUpon()
                    .appendQueryParameter("center", "${weatherEntry.lat}, ${weatherEntry.lon}")
                    .appendQueryParameter("zoom", "12")
                    .appendQueryParameter("size", "540x400")
                    .appendQueryParameter("scale", scale)
                    .appendQueryParameter("key", apiKey)
                    .build()
            val url = staticMapUri.toString()
            Log.d("tag", "map url: $url")
            return url
        }

    }

}