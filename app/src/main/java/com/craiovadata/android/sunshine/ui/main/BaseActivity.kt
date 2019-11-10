package com.craiovadata.android.sunshine.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.utilities.CityUtils
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

open class BaseActivity: AppCompatActivity() {
    var adViewMedRectangle: AdView? = null
    private var checkTimezoneDone = false
    companion object{
        const val ads_test_id = "ca-app-pub-3940256099942544/6300978111"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.craiovadata.android.sunshine.R.layout.activity_main)
        setSupportActionBar(toolbar)
        backImage.setImageResource(CityUtils.getBackResId(this))

        // init ads before observers
        initAds()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.craiovadata.android.sunshine.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            com.craiovadata.android.sunshine.R.id.action_settings -> {
//                if (BuildConfig.DEBUG) {
//                    viewModel.onRestartActivity()
//                } else
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            com.craiovadata.android.sunshine.R.id.action_privacy_policy -> {
//                if (BuildConfig.DEBUG) {
//                    viewModel.onPolicyPressed()
//                } else
                goToPrivacyPolicy()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAds() {
        MobileAds.initialize(this)
        //            getString(com.craiovadata.android.sunshine.R.string.admob_app_id))
        loadAdMedRectangle() // before observers

        val adRequest = AdRequest.Builder()
            .addTestDevice("B311809EC1E4139E4F40A0EF6C399759")  // Nokia 2
            .addTestDevice("9EDAF80E0B8DCB545330A07BD675095F")  // Moto G7
            .build()
        bannerAdView.loadAd(adRequest)
    }


    private fun loadAdMedRectangle() {
        val newAdView = AdView(this)
        newAdView.apply {
            adSize = AdSize.MEDIUM_RECTANGLE
            adUnitId = if (BuildConfig.DEBUG) ads_test_id
            else getString(com.craiovadata.android.sunshine.R.string.admob_med_rectangle_id)

            adListener = object : AdListener() {

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adViewMedRectangle = newAdView
                    updateAdapter()

                }
            }
        }
        val adRequest = AdRequest.Builder()
            .addTestDevice("B311809EC1E4139E4F40A0EF6C399759")  // Nokia 2
            .addTestDevice("9EDAF80E0B8DCB545330A07BD675095F")  // Moto G7
            .build()
        newAdView.loadAd(adRequest)
    }

     open fun updateAdapter() {

    }

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
            Uri.parse(getString(com.craiovadata.android.sunshine.R.string.link_privacy_policy))
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