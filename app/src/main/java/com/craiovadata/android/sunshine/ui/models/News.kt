package com.craiovadata.android.sunshine.ui.models

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.craiovadata.android.sunshine.R
import kotlinx.android.synthetic.main.news_card.view.*
import java.util.*

data class News(val queryString: String)
    : Base(-5, Base.TYPE.NEWS, Date(0)) {

    companion object {

        @JvmStatic
        fun bindNewsToUI(queryString: String, itemView: View) {

            val url = buildUrlGoogleNews(itemView.context, queryString)

            val webview = itemView.webview
            webview.loadUrl(BASE_STATIC_MAP_URL)
            webview.webViewClient = WebViewClient()
            webview.settings.javaScriptEnabled = true

//            itemView.buttonZoomPlus.setOnClickListener(clickListener)
        }

        private const val BASE_STATIC_MAP_URL = "https://news.google.com/?hl=ro&gl=RO&ceid=RO:ro"

        private fun buildUrlGoogleNews(context: Context, queryString: String): String {
//            val apiKey = context.getString(R.string.STATIC_MAP_KEY)
            val staticMapUri = Uri.parse(BASE_STATIC_MAP_URL).buildUpon()
                    .appendQueryParameter("q", queryString)
                    .appendQueryParameter("ceid", "RO:ro")
                    .build()
            val url = staticMapUri.toString()
            Log.d("tag", "news url: $url")
            return url
        }

    }

}