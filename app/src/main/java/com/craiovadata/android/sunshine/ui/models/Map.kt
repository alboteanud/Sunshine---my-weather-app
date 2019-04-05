package com.craiovadata.android.sunshine.ui.models

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.MyTimeZone
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import kotlinx.android.synthetic.main.map_card.view.*
import java.util.*

data class Map(val weatherEntry: WeatherEntry?)
    : Base(-4, Base.TYPE.MAP, Date(0)) {


    companion object {

        @JvmStatic
        fun bindMapToUI(weatherEntry: WeatherEntry?, itemView: View) {
            if (weatherEntry == null) return

            val lat = weatherEntry.lat
            val lon = weatherEntry.lon
            val prefs = itemView.context.getSharedPreferences("_", MODE_PRIVATE)
            val key = "key_zoom_level"
            val defaultZoomLevel = MyTimeZone.DEFAULT_ZOOM_LEVEL
            var zoomLevel = prefs.getInt(key, defaultZoomLevel)
            var url = buildUrlGoogleStaticMap(itemView.context, lat, lon, zoomLevel)
            loadMap(url, itemView.mapImageView)

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

        fun loadMap(url: String, mapImageView: ImageView) {
            Glide.with(mapImageView)
                    .load(url)
                    .into(mapImageView)
        }

        private const val BASE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?"

        private fun buildUrlGoogleStaticMap(context: Context, lat: Double, lon: Double, zoomLevel: Int): String {
            val apiKey = context.getString(R.string.STATIC_MAP_KEY)
            val scale = context.resources.getInteger(R.integer.scale_static_map).toString()
            val staticMapUri = Uri.parse(BASE_STATIC_MAP_URL).buildUpon()
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