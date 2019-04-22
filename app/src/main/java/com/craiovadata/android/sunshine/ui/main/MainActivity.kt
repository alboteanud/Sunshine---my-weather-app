package com.craiovadata.android.sunshine.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.ui.policy.PolicyActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.logDBvalues
import com.craiovadata.android.sunshine.utilities.NotifUtils.areNotificationsEnabled
import com.craiovadata.android.sunshine.utilities.Utils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//adb -e pull sdcard/Download/Sydney_ori_portrait.png /Users/danalboteanu/Desktop

class MainActivity : AppCompatActivity(), CardsAdapter.Listener {

    private var adView: AdView? = null
    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: MutableList<ListWeatherEntry>? = null
    private var multiDayEntries: MutableList<ListWeatherEntry>? = null
    private var listPosition = RecyclerView.NO_POSITION
    private val myHandler = Handler()


    private lateinit var adapter: CardsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        adapter = CardsAdapter(this, listOf(), this)
        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)

        MobileAds.initialize(this, getString(com.craiovadata.android.sunshine.R.string.admob_app_id))
        loadAdBanner() // before observers

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        observeCurrentWeather(viewModel)
        observeForecastData(viewModel)
        observeDaysForecastData(viewModel)
        observeAllEntriesData(viewModel)

        myHandler.postDelayed({
            if (listPosition == RecyclerView.NO_POSITION) {
                listPosition = 0
                recyclerView.smoothScrollToPosition(listPosition)
            }
        }, 1800)

    }

    private fun setBackgroundDelayed(delay: Long) {
        myHandler.postDelayed({
            val resId = Utils.getBackResId()
            backImage.setImageResource(resId)
        }, delay)
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
                        Map(currentWeatherEntry),
                        News("")

                ))
//        adView?.let { updates.add(4, Ads(adView)) }
        adapter.setUpdates(updates)
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
        setBackgroundDelayed(2500)
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

    override fun onNewsClicked(view: View) {

        startActivity(Intent(this, NewsActivity::class.java))
    }

    private fun observeAllEntriesData(viewModel: MainActivityViewModel) {
        if (!BuildConfig.DEBUG) return
        viewModel.allForecast.observe(this, androidx.lifecycle.Observer { entries ->
            if (entries != null && entries.isNotEmpty()) {
                entries.forEach { LogUtils.logEntry(this@MainActivity, it) }
            }
        })
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
                startActivity(Intent(this, PolicyActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
