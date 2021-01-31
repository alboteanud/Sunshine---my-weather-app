package com.craiovadata.android.sunshine.ui.policy

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.R
import kotlinx.android.synthetic.main.activity_policy.*
import kotlinx.android.synthetic.main.content_policy.*

class PrivacyPolicyActivity : AppCompatActivity() {

    private var mWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val urlPolicy = getString(R.string.link_privacy_policy)

        mWebView = policy_webview
        mWebView?.loadUrl(urlPolicy)
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


}
