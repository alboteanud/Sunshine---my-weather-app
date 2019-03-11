package com.example.android.sunshine.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.ui.test.TestActivity
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.Utils.getAdBannerId
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.graph_card.*
import kotlinx.android.synthetic.main.weather_card.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { weatherEntries ->
            Log.d(TAG, "Total values: " + weatherEntries.size)
            if (weatherEntries != null && weatherEntries.size != 0) {
                showWeatherDataView(weatherEntries)
                GraphCardUtils.initGraph(weatherEntries, graph1)
                WeatherCardUtils.initWeatherCard(cardWeather, weatherEntries)
            } else showLoading()



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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //        if (BuildConfig.DEBUG)
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_test_1) {
            startActivity(Intent(this, TestActivity::class.java))
        } else if (id == R.id.menu_test_2) {

        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        val TAG = "MainActivity"
    }

}
