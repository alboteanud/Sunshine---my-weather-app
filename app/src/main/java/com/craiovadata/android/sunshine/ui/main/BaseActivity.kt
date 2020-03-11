package com.craiovadata.android.sunshine.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.craiovadata.android.sunshine.utilities.NotifUtils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*

open class BaseActivity : AppCompatActivity() {
    var adViewMedRectangle: AdView? = null
    private var checkTimezoneDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        backImage.setImageResource(CityData.getBackResId(this))
        initAds()

    }

    private fun initAds() {
        if (BuildConfig.DEBUG) {
            bannerAdView.visibility = GONE
            return
        }
        MobileAds.initialize(this)
        bannerAdView.loadAd(AdRequest.Builder().build())
        loadAdMedRectangle() // before observers
    }

    private fun loadAdMedRectangle() {
        val newAdView = AdView(this)
        newAdView.apply {
            adSize = AdSize.MEDIUM_RECTANGLE
            adUnitId = getString(R.string.admob_med_rectangle_id)

            adListener = object : AdListener() {

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adViewMedRectangle = newAdView
                    updateAdapter()
                }
            }
        }
        newAdView.loadAd(AdRequest.Builder().build())
    }

    open fun updateAdapter() {}

    fun logAndWarnCurrentWeather(entries: List<WeatherEntry>) {
        // logs and warnings
        if (!BuildConfig.DEBUG) return

        LogUtils.logEntries(this, entries)

        val currentWeatherEntry = entries[0]
        warnIfCityNameWrong(currentWeatherEntry)
        if (!checkTimezoneDone) {
            LogUtils.checkIfTimezoneWrong(this, currentWeatherEntry, layoutAttention)
            checkTimezoneDone = true
        }
    }

    private fun warnIfCityNameWrong(
        currentWeatherEntry: WeatherEntry?
    ) {
        if (!BuildConfig.DEBUG) return
        if (currentWeatherEntry == null) return

        if (currentWeatherEntry.cityName.isEmpty()) return
        if (currentWeatherEntry.isCurrentWeather == 0) return  // only currentWeatherEntry contains cityName

        if (currentWeatherEntry.cityName != getString(R.string.app_name)) {  // ok
            //  !!! problem - wrong city name
            layoutAttention.visibility = View.VISIBLE
            val textToShow =
                "!!! orasul (primit de la OWM) se numeste: ${currentWeatherEntry.cityName}"
            layoutAttention.textViewWarnCityWrong.text = textToShow

//            layoutAttention.buttonWarning.setOnClickListener {
//                layoutAttention.visibility = View.GONE
//            }
        }
    }

    override fun onDestroy() {
        adViewMedRectangle?.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        adViewMedRectangle?.pause()
    }

    override fun onResume() {
        super.onResume()
        adViewMedRectangle?.resume()
    }

    override fun onStop() {
        super.onStop()
        NotifUtils.saveTimeAsLastNotification(this)
    }

    fun showRecyclerView() {
        loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        loading_indicator.visibility = View.VISIBLE
    }

    fun goToPrivacyPolicy() {
        val myLink =
            Uri.parse(getString(R.string.link_privacy_policy))
        val intent = Intent(Intent.ACTION_VIEW, myLink)
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        val isIntentSafe: Boolean = activities.isNotEmpty()
        if (isIntentSafe)
            startActivity(intent)
    }

}