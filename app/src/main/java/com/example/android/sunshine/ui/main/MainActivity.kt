package com.example.android.sunshine.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.BuildConfig
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.ui.update_models.*
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.Utils
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.example.android.sunshine.utilities.Utils.logDBvalues
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), Adapter.onItemClickListener {

    private var recyclerPosition = RecyclerView.NO_POSITION
    private lateinit var adView: AdView
    private var currentWeatherEntry: WeatherEntry? = null
    private var listWeatherEntries: MutableList<ListWeatherEntry>? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.sunshine.R.layout.activity_main)

        recyclerView.setHasFixedSize(true)
        val adapter = Adapter(this, listOf(), this)
        recyclerView.adapter = adapter

        // adView init before observers. Must not be null
        MobileAds.initialize(this, getString(com.example.android.sunshine.R.string.admob_app_id))
        adView = AdView(this)

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        observeCurrentWeather(viewModel, adapter)
        observeForecastData(viewModel, adapter)
        loadAdBanner()

        if (BuildConfig.DEBUG)
            for (i in 0 until TimeZone.getAvailableIDs().size) {
                val id = TimeZone.getAvailableIDs()[i]
                Log.d("tag", "timeZone $i $id")
//            TimeZone.getTimeZone(Time)
            }
    }

    private fun observeCurrentWeather(viewModel: MainActivityViewModel, adapter: Adapter) {
        InjectorUtils.provideRepository(this).initializeDataCW()
        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entry ->
            entry?.let {
                showRecyclerView()
                currentWeatherEntry = it
                updateAdapter(adapter)
                Log.d(TAG, "DB entry CW " + SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
                        .format(it.date) + "  temperature " + it.temp)
            } ?: showLoading()
        })
    }

    private fun observeForecastData(viewModel: MainActivityViewModel, adapter: Adapter) {
        InjectorUtils.provideRepository(this).initializeData()
        viewModel.forecast.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && !listEntries.isEmpty()) {
                listWeatherEntries = listEntries
                updateAdapter(adapter)
                logDBvalues(this, listEntries)
            }
        })
    }

    private fun positionRecyclerDelayed() {
        if (recyclerPosition == RecyclerView.NO_POSITION) {
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed({
                recyclerPosition = 0
                recyclerView.smoothScrollToPosition(recyclerPosition)
            }, 2000)
        }
    }

    private fun updateAdapter(adapter: Adapter) {
        val updatesList = mutableListOf<BaseUpdate>()
        // !!! pozitia in lista conteaza
        // Primele 3 sunt notificate de schimbare - fun onCelsiusFarClicked()
        currentWeatherEntry?.let { updatesList.add(CurrentWeatherUpdate(it)) }
        listWeatherEntries?.let { updatesList.add(GraphForecastUpdate(it)) }
        currentWeatherEntry?.let { updatesList.add(DetailsUpdate(it)) }

        updatesList.add(AdBannerUpdate(adView))
        currentWeatherEntry?.let { updatesList.add(MapUpdate(it)) }

        adapter.setUpdates(updatesList)
        positionRecyclerDelayed()
    }

    private fun setBackgroundImg() {
        val imgId = getBackResId(this)
        if (backgroundImageView.tag == imgId) return
        backgroundImageView.tag = imgId
        backgroundImageView.setImageResource(imgId)
    }

    private fun loadAdBanner() {
        adView.adSize = AdSize.MEDIUM_RECTANGLE
        adView.adUnitId = Utils.getAdBannerId(this)
        adView.loadAd(AdRequest.Builder().build())
    }

    override fun onResume() {
        adView.resume()
        super.onResume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun showRecyclerView() {
        loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        recyclerView.setVisibility(View.INVISIBLE)
        loading_indicator.setVisibility(View.VISIBLE)
    }

    override fun onStart() {
        super.onStart()
        InjectorUtils.provideRepository(this).initializeDataCW()
        setBackgroundImg()
    }

    override fun onStop() {
        super.onStop()
        InjectorUtils.provideRepository(this).resetInitializedCW()
    }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCelsiusFarClicked(view: View) {
        recyclerView.adapter?.notifyItemRangeChanged(0, 3)
    }


}
