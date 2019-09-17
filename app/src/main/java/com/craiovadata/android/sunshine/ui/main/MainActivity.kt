package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.logDBvalues
import com.craiovadata.android.sunshine.utilities.Utils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*

//adb -e pull sdcard/Download/Sydney_ori_portrait.png /Users/danalboteanu/Desktop

class MainActivity : AppCompatActivity(), CardsAdapter.Listener {
    private var adView: AdView? = null
    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: MutableList<ListWeatherEntry>? = null
    private var multiDayEntries: MutableList<ListWeatherEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
    private val handler = Handler()


    private lateinit var mAdapter: CardsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        MobileAds.initialize(this, getString(com.craiovadata.android.sunshine.R.string.admob_app_id))
        loadAdBanner() // before observers

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        observeCurrentWeather(viewModel)
        observeForecastData(viewModel)
        observeDaysForecastData(viewModel)
//        observeAllEntriesData(viewModel)

    }


    private class MyLinearLayoutManager(private val context: Context) : LinearLayoutManager(context) {

        // Force new items appear at the top
        override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
            super.onItemsAdded(recyclerView, positionStart, itemCount)
            scrollToPosition(0)
        }

    }

    private fun setBackgroundDelayed() {
        handler.postDelayed({
            val resId = Utils.getBackResId()
            backImage.setImageResource(resId)
        }, 2000)
    }

    private fun observeCurrentWeather(viewModel: MainActivityViewModel) {

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entries ->
            if (entries != null && entries.size > 0) {
                showRecyclerView()
                currentWeatherEntry = entries[0]
                updateAdapter()
                logDBvalues(this, mutableListOf(), entries)
            } else showLoading()
        })
    }

    private fun observeForecastData(viewModel: MainActivityViewModel) {

        viewModel.nextHoursWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                showRecyclerView()
                graphWeatherEntries = listEntries
                updateAdapter()
                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun observeDaysForecastData(viewModel: MainActivityViewModel) {
        viewModel.midDayWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                multiDayEntries = listEntries
                updateAdapter()
                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun updateAdapter() {
        val updates = mutableListOf<Base>()
        // Primele 4 sunt notificate de schimbare °C|°F - onCelsiusFarClicked
        updates.addAll(
                listOf(
                        CurrentWeather(currentWeatherEntry),
                        Graph(graphWeatherEntries),
                        Details(currentWeatherEntry),
                        MultiDay(multiDayEntries),
                        Ads(adView),
                        Map(currentWeatherEntry)
                ))
//        adView?.let { updates.add(4, Ads(adView)) }
        mAdapter.setUpdates(updates)
//        recyclerView.scrollToPosition(0)
    }

    private fun loadAdBanner() {
        adView = AdView(this)
        adView?.adSize = AdSize.MEDIUM_RECTANGLE
        adView?.adUnitId = Utils.getAdBannerId(this)
//        newAdView.adListener = object : AdListener() {
//
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                adView = newAdView
//                updateAdapter()
//
//            }
//        }
        adView?.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        InjectorUtils.provideRepository(this).initializeDataCW()
        adView?.resume()
        setBackgroundDelayed()
        super.onResume()
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
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

//    private fun observeAllEntriesData(viewModel: MainActivityViewModel) {
//        if (!BuildConfig.DEBUG) return
//        viewModel.allForecast.observe(this, androidx.lifecycle.Observer { entries ->
//            if (entries != null && !entries.isEmpty()) {
//                entries.forEach { LogUtils.logEntry(this@MainActivity, it) }
//            }
//        })
//    }


}
