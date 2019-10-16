package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
//import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.logDBvalues
import com.craiovadata.android.sunshine.utilities.LogUtils.logEntry
import com.craiovadata.android.sunshine.utilities.Utils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//adb -e pull sdcard/Download/Sydney_ori_portrait.png /Users/danalboteanu/Desktop

class MainActivity : AppCompatActivity(), CardsAdapter.Listener {

    private var adViewMedRectangle: AdView? = null
    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: MutableList<ListWeatherEntry>? = null
    private var multiDayEntries: MutableList<ListWeatherEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
//    private val handler = Handler()
    private lateinit var mAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        MobileAds.initialize(this)
//            getString(com.craiovadata.android.sunshine.R.string.admob_app_id))
        loadAdMedRectangle() // before observers

        val adRequest = AdRequest.Builder()
            .addTestDevice("B311809EC1E4139E4F40A0EF6C399759")  // Nokia 2
            .addTestDevice("9EDAF80E0B8DCB545330A07BD675095F")  // Moto G7
            .build()
        bannerAdView.loadAd(adRequest)


        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW(this)
        InjectorUtils.provideRepository(this).initializeData()

        observeCurrentWeather(viewModel)
        observeForecastData(viewModel)
        observeDaysForecastData(viewModel)
//        observeAllEntriesData(viewModel)

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
                if (BuildConfig.DEBUG) {
                    InjectorUtils.provideRepository(this).forceInitializeDataCW()
                } else
                    goToPrivacyPolicy()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToPrivacyPolicy() {
        val myLink = Uri.parse(getString(R.string.link_privacy_policy))
        val intent = Intent(Intent.ACTION_VIEW, myLink)
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        val isIntentSafe: Boolean = activities.isNotEmpty()
        if (isIntentSafe)
            startActivity(intent)
    }

    private class MyLinearLayoutManager(private val context: Context) :
        LinearLayoutManager(context) {

        // Force new items appear at the top
        override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
            super.onItemsAdded(recyclerView, positionStart, itemCount)
            scrollToPosition(0)
        }
    }

    private fun setBackgroundDelayed() {
//        handler.postDelayed({
        val resId = Utils.getBackResId()
        backImage.setImageResource(resId)
//        }, 1500)
    }

    private fun observeCurrentWeather(viewModel: MainActivityViewModel) {

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entries ->
            if (entries != null && entries.size > 0) {
                showRecyclerView()
                currentWeatherEntry = entries[0]
                updateAdapter()
                logEntry(this, entries[0])
                if (BuildConfig.DEBUG) {
                    textCity.text = currentWeatherEntry?.cityName ?: "not found"
                }
            } else showLoading()
        })
    }

    private fun observeForecastData(viewModel: MainActivityViewModel) {

        viewModel.nextHoursWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                showRecyclerView()
                graphWeatherEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun observeDaysForecastData(viewModel: MainActivityViewModel) {
        viewModel.midDayWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.size > 0) {
                multiDayEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
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
//                        Ads(adViewMedRectangle),
                Map(currentWeatherEntry),
                News("")
            )
        )
        adViewMedRectangle?.let { updates.add(updates.size, Ads(adViewMedRectangle)) }
        mAdapter.setUpdates(updates)
    }

    private fun loadAdMedRectangle() {
        val newAdView = AdView(this)
        newAdView.apply {
            adSize = AdSize.MEDIUM_RECTANGLE
            adUnitId = if (BuildConfig.DEBUG) ads_test_id
            else getString(R.string.admob_med_rectangle_id)

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

    override fun onRestart() {
        super.onRestart()
        // resetInitializedCW() in onStop
        val repo = InjectorUtils.provideRepository(this)
        repo.initializeDataCW(this)
//        repo.

    }

    override fun onResume() {

        adViewMedRectangle?.resume()
        setBackgroundDelayed()
        super.onResume()
    }

    override fun onPause() {
        InjectorUtils.provideRepository(this).resetInitializedCW()
        adViewMedRectangle?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adViewMedRectangle?.destroy()
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
        const val ads_test_id = "ca-app-pub-3940256099942544/6300978111"
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


    override fun onNewsClicked(view: View) {
        startActivity(Intent(this, NewsActivity::class.java))
    }


}
