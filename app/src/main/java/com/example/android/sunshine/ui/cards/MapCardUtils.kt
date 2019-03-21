package com.example.android.sunshine.ui.cards

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.WeatherEntry
import kotlinx.android.synthetic.main.map_card.view.*

object MapCardUtils {

    fun bindMapToUI(weatherEntry: WeatherEntry, itemView: View) {

        val url = buildUrlGoogleStaticMap(itemView.context, weatherEntry)
        Glide.with(itemView.mapImageView)
                .load(url)
                .into(itemView.mapImageView)
    }

    private const val BASE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?"

    private fun buildUrlGoogleStaticMap(context: Context, weatherEntry: WeatherEntry): String {
        val GOOGLE_STATIC_MAP_API_KEY = context.getString(R.string.STATIC_MAP_KEY)
        val scale = context.resources.getInteger(R.integer.scale_static_map).toString()
        val staticMapUri = Uri.parse(BASE_STATIC_MAP_URL).buildUpon()
                .appendQueryParameter("center", "${weatherEntry.lat}, ${weatherEntry.lon}")
                .appendQueryParameter("zoom", "12")
                .appendQueryParameter("size", "540x400")
                .appendQueryParameter("scale", scale)
                .appendQueryParameter("key", GOOGLE_STATIC_MAP_API_KEY)
                .build()
        val url = staticMapUri.toString()
        Log.d("tag", "map url: $url")
        return url
    }


}
