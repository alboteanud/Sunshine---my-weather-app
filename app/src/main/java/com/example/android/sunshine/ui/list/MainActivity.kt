package com.example.android.sunshine.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.ui.detail.DetailActivity
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.SunshineDateUtils
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import com.example.android.sunshine.utilities.Utils.getAdBannerId
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.graph_card.*
import kotlinx.android.synthetic.main.weather_card.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(main_toolbar)

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCurrentWeather()
        InjectorUtils.provideRepository(this).initializeData()

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { weatherEntries ->
            Log.d(TAG, "Total values: " + weatherEntries.size)
            if (weatherEntries != null && weatherEntries.size > 0) {
                showWeatherDataView(weatherEntries)
                WeatherCardUtils.initWeatherCard(cardWeather, weatherEntries)
                logDBvalues(weatherEntries)
            } else showLoading()
        })

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { weatherEntries ->
            Log.d(TAG, "Total values: " + weatherEntries.size)
            if (weatherEntries != null && weatherEntries.size > 0) {
                showWeatherDataView(weatherEntries)
                GraphCardUtils.initGraph(weatherEntries, my_graph)
                logDBvalues(weatherEntries)
            }
        })



        initAdBanner()
        val imgId = getBackResId(this)
        backgroundImageView.setImageResource(imgId)

        detailsTextView.setOnClickListener{
            startActivity(Intent(this, DetailActivity::class.java))
        }
    }

    private fun logDBvalues(weatherEntries: List<ListWeatherEntry>) {
        if (!BuildConfig.DEBUG) return

        weatherEntries.forEach {
            val mills = SunshineDateUtils.getCityTimeMills(this, it.date.time)
            val utcDt = SimpleDateFormat(" dd MMM HH:mm").format(it.date)
            val cityDt = SimpleDateFormat(" dd MMM HH:mm").format(mills)
//val isCurrentW = it.
            val temperature = SunshineWeatherUtils.formatTemperature(this, it.temp)
            Log.d(TAG, "DB entry: " + utcDt + "  cityDt " + cityDt + "  temp " + temperature)
        }

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

    private fun showWeatherDataView(weatherEntries: MutableList<ListWeatherEntry>) {
        // First, hide the loading indicator
        pb_loading_indicator.visibility = View.INVISIBLE
        // Finally, make sure the weather data is visible
        content_main.visibility = View.VISIBLE

    }

    private fun showLoading() {
        // Then, hide the weather data
        content_main.visibility = View.INVISIBLE
        // Finally, show the loading indicator
        pb_loading_indicator.visibility = View.VISIBLE
    }

    companion object {
        val TAG = "MainActivity"
    }

}
