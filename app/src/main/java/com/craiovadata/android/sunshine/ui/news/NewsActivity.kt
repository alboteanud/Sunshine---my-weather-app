package com.craiovadata.android.sunshine.ui.news

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.content_news.*

class NewsActivity : AppCompatActivity() {
    private val urlNews = "https://news.google.com"
//    private val urlNews = "https://news.google.com/foryou?hl=en&gl=US&ceid=US:en"
//    private val urlNews = "https://news.google.com/foryou?q=craiova"
//    private val urlNews = "https://news.google.com/foryou"

    private var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mWebView = news_webview
        mWebView?.loadUrl(urlNews)
        mWebView?.webViewClient = WebViewClient()
        mWebView?.settings?.javaScriptEnabled = true

//        fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.blackSemi7));
//        fab.setOnClickListener{
//            NavUtils.navigateUpFromSameTask(this);
//        }
    }

    override fun onPause() {
        super.onPause()
        mWebView?.onPause()
        mWebView?.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        mWebView?.onResume()
        mWebView?.resumeTimers()
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
