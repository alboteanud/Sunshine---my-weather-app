package com.craiovadata.android.sunshine.ui.adpterModels

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import java.util.*
import com.bumptech.glide.request.RequestOptions
import com.craiovadata.android.sunshine.CityData.DEFAULT_ZOOM_LEVEL
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.utilities.LogUtils.log
import kotlinx.android.synthetic.main.card_map.view.*


data class Map(val weatherEntry: WeatherEntry?) : Base(
//    -4,
    TYPE.MAP) {

    companion object {

        @JvmStatic
        fun bindMapToUI(weatherEntry: WeatherEntry?, itemView: View) {

            if (weatherEntry == null) return

            val lat = weatherEntry.lat
            val lon = weatherEntry.lon
            val prefs = itemView.context.getSharedPreferences("_", MODE_PRIVATE)
            val key = "key_zoom_level"
            var zoomLevel = prefs.getInt(key, DEFAULT_ZOOM_LEVEL)
            var url =
                buildUrlGoogleStaticMap(
                    itemView.context,
                    lat,
                    lon,
                    zoomLevel
                )
            loadMap(
                url,
                itemView
            )

            val clickListener = View.OnClickListener {
                when (it.id) {
                    R.id.buttonZoomPlus -> {
                        zoomLevel++
                        if (zoomLevel >= 14) zoomLevel = 14
                    }
                    R.id.buttonZoomMinus -> {
                        zoomLevel--
                        if (zoomLevel <= 9) zoomLevel = 9
                    }
                }
                url =
                    buildUrlGoogleStaticMap(
                        itemView.context,
                        lat,
                        lon,
                        zoomLevel
                    )
                loadMap(
                    url,
                    itemView.mapImageView
                )
                prefs.edit().putInt(key, zoomLevel).apply()

            }
            itemView.buttonZoomPlus.setOnClickListener(clickListener)
            itemView.buttonZoomMinus.setOnClickListener(clickListener)
        }

        private fun loadMap(url: String, itemView: View) {
            val mapImageView = itemView.mapImageView
            val requestOptions = RequestOptions()
            .placeholder(
                R.drawable.ic_map)
            .error(R.drawable.ic_map)

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
            val apiKey = context.getString(R.string.GOOGLE_API_KEY)
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
            log("map url: $url")
            return url
        }

    }

}