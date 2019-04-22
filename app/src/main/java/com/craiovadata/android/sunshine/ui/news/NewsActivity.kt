package com.craiovadata.android.sunshine.ui.news

import android.content.res.ColorStateList
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.craiovadata.android.sunshine.R
import kotlinx.android.synthetic.main.activity_news.*

import kotlinx.android.synthetic.main.content_news.*

class NewsActivity : AppCompatActivity() {
//    private val urlNews = "https://news.google.com/?hl=ro&gl=RO&ceid=RO:ro"
//    private val urlNews = "https://news.google.com/foryou?hl=en&gl=US&ceid=US:en"
//    private val urlNews = "https://news.google.com/foryou?q=craiova"
    private val urlNews = "https://news.google.com/foryou"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
//        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webview.loadUrl(urlNews)
        webview.webViewClient = WebViewClient()
        webview.settings.javaScriptEnabled = true

//        fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.blackSemi7));
//        fab.setOnClickListener{
//            NavUtils.navigateUpFromSameTask(this);
//        }
    }


//    private fun buildUrlGoogleNews(): String {
//        val cityName = "Craiova"
//        val staticMapUri = Uri.parse(urlNews).buildUpon()
////                .appendQueryParameter("q", cityName)
////                .appendQueryParameter("ceid", "RO:ro")
//                .build()
//        val url = staticMapUri.toString()
//        Log.d("tag", "news url: $url")
//        return url
//    }

}
