package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils.logEntries
import com.craiovadata.android.sunshine.utilities.Utils
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import androidx.lifecycle.*


class MainActivity : AppCompatActivity(), CardsAdapter.Listener{

    private var adViewMedRectangle: AdView? = null
    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: List<ListWeatherEntry>? = null
    private var multiDayEntries: List<ListWeatherEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
    private lateinit var mAdapter: CardsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.craiovadata.android.sunshine.R.layout.activity_main)
        setSupportActionBar(toolbar)

        backImage.setImageResource(Utils.getBackResId())

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        // init ads before observers
        initAds()

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this@MainActivity, factory).get(MainViewModel::class.java)

        observeCurrentWeather(viewModel)
        observeNextHoursData(viewModel)
        observeDaysForecastData(viewModel)

        lifecycle.addObserver(viewModel)

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
                if (BuildConfig.DEBUG) {
                    viewModel.onPolicyPressed()
                } else
                    goToPrivacyPolicy()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToPrivacyPolicy() {
        val myLink = Uri.parse(getString(com.craiovadata.android.sunshine.R.string.link_privacy_policy))
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

    private fun observeCurrentWeather(viewModel: MainViewModel) {
        viewModel.currentWeatherObservable.observe(this,
            androidx.lifecycle.Observer<List<WeatherEntry>> { entries ->
                if (entries != null && entries.isNotEmpty()) {
                    showRecyclerView()
                    currentWeatherEntry = entries[0]
                    updateAdapter()
                    logEntries(this, entries)
                } else showLoading()
            })
    }

    private fun observeNextHoursData(viewModel: MainViewModel) {

        viewModel.nextHoursWeatherObservable.observe(this, Observer { listEntries ->
            if (listEntries != null && listEntries.isNotEmpty()) {
                showRecyclerView()
                graphWeatherEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun observeDaysForecastData(viewModel: MainViewModel) {
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

    override fun onDestroy() {
        adViewMedRectangle?.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        adViewMedRectangle?.pause()
    }

    private fun showRecyclerView() {
        loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        recyclerView.visibility = View.INVISIBLE
        loading_indicator.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "MainActivity"
        const val ads_test_id = "ca-app-pub-3940256099942544/6300978111"
    }

    override fun onCelsiusFarClicked(view: View) {
        recyclerView.adapter?.notifyItemRangeChanged(0, 4)
    }

    override fun onNewsClicked(view: View) {
        startActivity(Intent(this, NewsActivity::class.java))
    }

}
