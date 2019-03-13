package com.example.android.sunshine.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.sunshine.R
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.Utils.getAdBannerId
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.example.android.sunshine.utilities.Utils.logDBvalues
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_weather_card.*
import kotlinx.android.synthetic.main.graph_card.*
import kotlinx.android.synthetic.main.weather_card.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer {weatherEntries ->
            if (weatherEntries != null) {
                showNestedScrollView()
                WeatherCardUtils.initWeatherCard(cardWeather, weatherEntries)
                WeatherDetailsCardUtils.bindWeatherToUI(cardDetailsWeather, weatherEntries)
                Log.d(MainActivity.TAG, "DB entry CW: " + SimpleDateFormat(" dd MMM HH:mm").format(weatherEntries.date) + "  temp " + weatherEntries.temp)
//                logDBvalues(this, weatherEntries)
            }
        })

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { weatherEntries ->
            if (weatherEntries != null && weatherEntries.size > 0) {
                showNestedScrollView()
                GraphCardUtils.initGraph(weatherEntries, my_graph)
                logDBvalues(this, weatherEntries)
            }
        })



        initAdBanner()
        val imgId = getBackResId(this)
        backgroundImageView.setImageResource(imgId)

    }

    private fun initAdBanner() {
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        AdView(this).apply {
            adUnitId = getAdBannerId(this@MainActivity)
            adSize = AdSize.SMART_BANNER
//            adContainer.addView(this)
//            loadAd(AdRequest.Builder().build())
        }
    }

    private fun showNestedScrollView() {
        pb_loading_indicator.visibility = View.INVISIBLE
        nestedScrollView.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        InjectorUtils.provideRepository(this).initializeDataCW()
    }

    override fun onStop() {
        super.onStop()
        InjectorUtils.provideRepository(this).resetInitializedCW()
    }

    companion object {
        val TAG = "MainActivity"
    }

}
