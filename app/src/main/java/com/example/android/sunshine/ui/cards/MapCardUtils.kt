package com.example.android.sunshine.ui.cards

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.android.sunshine.R
import kotlinx.android.synthetic.main.map_card.view.*
import java.net.MalformedURLException
import java.net.URL

object MapCardUtils {

    fun bindMapToUI(lat: Double, lon: Double, cardView: View) {

        val url = buildUrlGoogleStaticMap(cardView.context, lat, lon)
        Glide.with(cardView)
                .load(url)
                .into(cardView.mapImageView)

    }

    private const val GOOGLE_STATIC_MAP_API_KEY = "AIzaSyB3W6TqecOLdHp8lcW4X9SCEmyYDr9BgZ0"
    // proiect Sunshine 2019 - anca.scobaru@gmail.com

    private const val BASE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap?"

    private fun buildUrlGoogleStaticMap(context: Context, lat: Double, lon: Double): URL? {
        val scale = context.resources.getInteger(R.integer.scale_static_map).toString()
        val staticMapUri = Uri.parse(BASE_STATIC_MAP_URL).buildUpon()
                .appendQueryParameter("center", "$lat, $lon")
                .appendQueryParameter("zoom", "12")
                .appendQueryParameter("size", "400x400")
                .appendQueryParameter("scale", scale)
                .appendQueryParameter("key", GOOGLE_STATIC_MAP_API_KEY)
                .build()

        try {
            val url = URL(staticMapUri.toString())
            Log.v("MapCardUtils", "static map URL: $url")
            return url
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }

    }


}
