package com.example.android.sunshine.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.ui.recyclerViewTest.*
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.Utils
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.example.android.sunshine.utilities.Utils.logDBvalues
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), Adapter.onItemClickListener {


    private lateinit var viewModel: MainActivityViewModel
    private var mPosition = RecyclerView.NO_POSITION
    private var currentWeatherUpdate: CurrentWeatherUpdate? = null
    private var detailsUpdate: DetailsUpdate? = null
    private var graphForecastUpdate: GraphForecastUpdate? = null
    private var mapUpdate: MapUpdate? = null
    private var adsUpdate: AdsUpdate? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.sunshine.R.layout.activity_main)

        recyclerView.setHasFixedSize(true)
        val adapter = Adapter(this, listOf(), this)
        recyclerView.adapter = adapter

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entry ->
            if (entry != null) {
                showRecyclerView()
                currentWeatherUpdate = CurrentWeatherUpdate(entry)
                detailsUpdate = DetailsUpdate(entry)
                mapUpdate = MapUpdate(entry)
                updateAdapter(adapter)
                Log.d(TAG, "DB entry CW " + SimpleDateFormat("dd MMM HH:mm").format(entry.date) + "  temp " + entry.temp)
            } else showLoading()
        })

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { listWeatherEntries ->
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0
            recyclerView.smoothScrollToPosition(mPosition)
            if (listWeatherEntries != null && listWeatherEntries.size > 0) {
                showRecyclerView()
                logDBvalues(this, listWeatherEntries)
                graphForecastUpdate = GraphForecastUpdate(listWeatherEntries)
                updateAdapter(adapter)
            } // else showLoading()
        })


        initAdBanner(adapter)
        setBackgroundImg()
    }

    private fun setBackgroundImg(){
        val imgId = getBackResId(this)
        backgroundImageView.setImageResource(imgId)
    }

    private fun initAdBanner(adapter: Adapter) {
        MobileAds.initialize(this, getString(com.example.android.sunshine.R.string.admob_app_id))
        adView = AdView(this)
        adView?.adSize = AdSize.MEDIUM_RECTANGLE
        adView?.adUnitId = Utils.getAdBannerId(this)
        adView?.loadAd(AdRequest.Builder().build())
        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                adsUpdate = AdsUpdate(-2, Date(), adView)
                updateAdapter(adapter)
            }
        }
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView?.removeAllViews();
        adView?.destroy()
        super.onDestroy()
    }

    private fun updateAdapter(adapter: Adapter) {
        val list = mutableListOf<Update>()
        currentWeatherUpdate?.let { list.add(it) }
        detailsUpdate?.let { list.add(it) }
        graphForecastUpdate?.let { list.add(it) }
        adsUpdate?.let { list.add(it) }
        mapUpdate?.let { list.add(it) }
        adapter.setUpdates(list)
    }

    private fun showRecyclerView() {
        pb_loading_indicator.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        // Then, hide the weather data
        recyclerView.setVisibility(View.INVISIBLE)
        // Finally, show the loading indicator
        pb_loading_indicator.setVisibility(View.VISIBLE)
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
        const val TAG = "MainActivity"
    }

    override fun onWeatherImageClicked() {
    }




}
