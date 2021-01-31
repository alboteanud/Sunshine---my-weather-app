package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.CityData.isTestMode
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.models.WeatherEntry
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.craiovadata.android.sunshine.utilities.NotifUtils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import java.util.concurrent.TimeUnit


open class BaseActivity : AppCompatActivity() {
    var adViewMedRectangle: AdView? = null
    private var checkTimezoneDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setBackground()
        initAds()
        initStrictMode()
    }

    private fun initStrictMode() {
        if (!isTestMode) return
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
//                .penaltyDeath()
                .build()
        )
    }

    private fun setBackground() {
        //    backImage.setImageResource(CityData.getBackResId(this))     // not recommended as it decodes on MainThread
        val resId = CityData.getBackResId(this)
        val backDrawable = ContextCompat.getDrawable(this, resId)
        backImage.setImageDrawable(backDrawable)
    }

    private fun initAds() {
        if (isTestMode) {
            bannerAdView.visibility = GONE
            return
        }
        MobileAds.initialize(this)
        loadAdMedRectangle() // before observers

        val savedDate = getPreferences(Context.MODE_PRIVATE).getLong("savedDateAds", 0L)
        if (savedDate == 0L) {
            getPreferences(Context.MODE_PRIVATE).edit()
                .putLong("savedDateAds", System.currentTimeMillis()).apply()
            bannerAdView.visibility = GONE
            return
        }
        val oneDaySinceInstall = System.currentTimeMillis() - savedDate > TimeUnit.DAYS.toMillis(1)
        if (!oneDaySinceInstall) {
            bannerAdView.visibility = GONE
            return
        }

        bannerAdView.loadAd(AdRequest.Builder().build())
    }

    private fun loadAdMedRectangle() {
        val newAdView = AdView(this)
        newAdView.apply {
            adSize = AdSize.MEDIUM_RECTANGLE
            adUnitId = getString(R.string.admob_med_rect)

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
        if (!isTestMode) return

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
        if (!isTestMode) return
        if (currentWeatherEntry == null) return

        if (currentWeatherEntry.cityName.isEmpty()) return
        if (currentWeatherEntry.isCurrentWeather == 0) return  // only currentWeatherEntry contains cityName

        if (currentWeatherEntry.cityName != getString(R.string.app_name)) {  // ok
            //  !!! problem - wrong city1 name
            layoutAttention.visibility = View.VISIBLE
            val textToShow =
                "orasul de la OWM se numeste: ${currentWeatherEntry.cityName} !"
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

    fun makeRequestsForMultipleCities() {
        // make a request for multiple cities weather - pt traducere coduri de vreme
//                    if (citiesIndexIncrement > 20)
//                        handler.removeCallbacksAndMessages(null)
//                    else{
//                    private val timedTask: Runnable = object : Runnable {
//                        override fun run() {
//                            if (citiIndexStart + citiesIndexIncrement < CityIds.cityIds2.size - 21) {
//                                myViewModel.forceSyncWeather(citiIndexStart + citiesIndexIncrement)
//                                citiesIndexIncrement += 20
//                                handler.postDelayed(this, 6 * 1000)
//                            }
//
//                        }
//                    }
//                        handler.post(timedTask)
//                        Toast.makeText(this, "getting data from multiple cities", Toast.LENGTH_LONG).show()
//                    }
    }

//    val citiIndexStart = 0
//    var citiesIndexIncrement = 0
//    val handler = Handler()
//       const val languageParamMultipleCitiesTest = "es"

}