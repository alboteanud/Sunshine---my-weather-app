package com.craiovadata.android.sunshine.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_privacy_policy -> {
                goToPrivacyPolicy()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        LogUtils.warnIfCityNameWrong(this, currentWeatherEntry, layoutAttention)
        if (!checkTimezoneDone) {
            LogUtils.checkIfTimezoneWrong(this, currentWeatherEntry, layoutAttention)
            checkTimezoneDone = true
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

    fun showRecyclerView() {
        loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        loading_indicator.visibility = View.VISIBLE
    }

    private fun goToPrivacyPolicy() {
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