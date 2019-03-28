package com.craiovadata.android.sunshine.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.logDBvalues
import com.craiovadata.android.sunshine.utilities.Utils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CardsAdapter.Listener {
    private var adView: AdView? = null
    private var currentWeatherEntry: WeatherEntry? = null
    private var listWeatherEntries: MutableList<ListWeatherEntry>? = null
    private var dayEntries: MutableList<ListWeatherEntry>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBackgroundDelayed(9 * 1000)

        recyclerView.setHasFixedSize(true)
        val adapter = CardsAdapter(this, listOf(), this)
        recyclerView.adapter = adapter

        MobileAds.initialize(this, getString(com.craiovadata.android.sunshine.R.string.admob_app_id))
        loadAdBanner() // before observers

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        observeCurrentWeather(viewModel, adapter)
        observeForecastData(viewModel, adapter)
        observeDaysForecastData(viewModel, adapter)
        observeAllEntriesData(viewModel)

    }

    private fun setBackgroundDelayed(delay: Long) {
        Handler().postDelayed({
            val resId = Utils.getBackResId()
            backImage.setImageResource(resId)
        }, delay)
    }

    private fun observeCurrentWeather(viewModel: MainActivityViewModel, cardsAdapter: CardsAdapter) {

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entries ->
            if (entries != null && entries.size > 0) {
                showRecyclerView()
                currentWeatherEntry = entries[0]
                updateAdapter(cardsAdapter)
                logDBvalues(this, mutableListOf(), entries)
            } else showLoading()
        })
    }

    private fun observeForecastData(viewModel: MainActivityViewModel, cardsAdapter: CardsAdapter) {

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                showRecyclerView()
                listWeatherEntries = listEntries
                updateAdapter(cardsAdapter)
                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun observeDaysForecastData(viewModel: MainActivityViewModel, cardsAdapter: CardsAdapter) {
        viewModel.forecastDays.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                dayEntries = listEntries
                updateAdapter(cardsAdapter)
                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    // for BuildConfig.DEBUG
    private fun observeAllEntriesData(viewModel: MainActivityViewModel) {
        if (!BuildConfig.DEBUG) return
        viewModel.allForecast.observe(this, androidx.lifecycle.Observer { entries ->
            if (entries != null && !entries.isEmpty()) {
                entries.forEach { LogUtils.logEntry(this@MainActivity, it) }
            }
        })
    }

    private fun updateAdapter(cardsAdapter: CardsAdapter) {
        val updatesList = mutableListOf<Base>()
        // Primele 4 sunt notificate de schimbare °C|°F - onCelsiusFarClicked
        updatesList.addAll(listOf(
                CurrentWeather(currentWeatherEntry),
                Graph(listWeatherEntries),
                Details(currentWeatherEntry),
                MultiDay(dayEntries),
                Ads(adView),
                Map(currentWeatherEntry)
        ))
        cardsAdapter.setUpdates(updatesList)

        recyclerView.scrollToPosition(0)
    }

    private fun loadAdBanner() {
        adView = AdView(this)
        adView?.adSize = AdSize.MEDIUM_RECTANGLE
        adView?.adUnitId = Utils.getAdBannerId(this)
        adView?.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        InjectorUtils.provideRepository(this).initializeDataCW()
        if (!BuildConfig.DEBUG)
            adView?.resume()
        super.onResume()
    }

    override fun onPause() {
        if (!BuildConfig.DEBUG)
            adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        if (!BuildConfig.DEBUG)
            adView?.destroy()
        super.onDestroy()
    }

    private fun showRecyclerView() {
        loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        loading_indicator.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        InjectorUtils.provideRepository(this).resetInitializedCW()
    }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCelsiusFarClicked(view: View) {
        recyclerView.adapter?.notifyItemRangeChanged(0, 4)
    }


}
