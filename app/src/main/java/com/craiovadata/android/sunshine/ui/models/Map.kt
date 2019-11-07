package com.craiovadata.android.sunshine.ui.models

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import kotlinx.android.synthetic.main.map_card.view.*
import java.util.*
import com.bumptech.glide.request.RequestOptions
import com.craiovadata.android.sunshine.R


data class Map(val weatherEntry: WeatherEntry?) : Base(-4, Base.TYPE.MAP, Date(0)) {


    companion object {

        private const val DEFAULT_ZOOM_LEVEL: Int = 12

        @JvmStatic
        fun bindMapToUI(weatherEntry: WeatherEntry?, itemView: View) {

            if (weatherEntry == null) return

            val lat = weatherEntry.lat
            val lon = weatherEntry.lon
            val prefs = itemView.context.getSharedPreferences("_", MODE_PRIVATE)
            val key = "key_zoom_level"
            val defaultZoomLevel = DEFAULT_ZOOM_LEVEL
            var zoomLevel = prefs.getInt(key, defaultZoomLevel)
            var url = buildUrlGoogleStaticMap(itemView.context, lat, lon, zoomLevel)
            loadMap(url, itemView)

            val clickListener = View.OnClickListener {
                when (it.id) {
                    R.id.buttonZoomPlus -> {
                        zoomLevel++
                        if (zoomLevel >= 15) zoomLevel = 15
                    }
                    R.id.buttonZoomMinus -> {
                        zoomLevel--
                        if (zoomLevel <= 9) zoomLevel = 9
                    }
                }
                url = buildUrlGoogleStaticMap(itemView.context, lat, lon, zoomLevel)
                loadMap(url, itemView.mapImageView)
                prefs.edit().putInt(key, zoomLevel).apply()

            }
            itemView.buttonZoomPlus.setOnClickListener(clickListener)
            itemView.buttonZoomMinus.setOnClickListener(clickListener)
        }

        private fun loadMap(url: String, itemView: View) {
            val mapImageView = itemView.mapImageView
            val requestOptions = RequestOptions()
            .placeholder(
                com.craiovadata.android.sunshine.R.drawable.ic_map)
            .error(com.craiovadata.android.sunshine.R.drawable.ic_map)

            Glide.with(mapImageView)
                .load(url)
                .apply(requestOptions)
                .into(mapImageView)
        }

        private fun buildUrlGoogleStaticMap(
            context: Context,
            lat: Double,
            lon: Double,
            zoomLevel: Int
        ): String {
            val apiKey = context.getString(R.string.STATIC_GMAP_KEY)
            val scale = context.resources.getInteger(R.integer.scale_static_map).toString()
            val baseUrl = context.getString(R.string.BASE_STATIC_MAP_URL)
            val staticMapUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("center", "$lat, $lon")
                .appendQueryParameter("zoom", zoomLevel.toString())
                .appendQueryParameter("size", "600x500")
                .appendQueryParameter("scale", scale)
                .appendQueryParameter("key", apiKey)
                .build()
            val url = staticMapUri.toString()
            Log.d("tag", "map url: $url")
            return url
        }

    }

}