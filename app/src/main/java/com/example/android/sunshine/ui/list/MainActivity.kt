package com.example.android.sunshine.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sunshine.R
import com.example.android.sunshine.ui.recyclerViewTest.CardsAdapter
import com.example.android.sunshine.ui.recyclerViewTest.CurrentWeatherUpdate
import com.example.android.sunshine.ui.recyclerViewTest.DetailsUpdate
import com.example.android.sunshine.utilities.InjectorUtils
import com.example.android.sunshine.utilities.Utils.getAdBannerId
import com.example.android.sunshine.utilities.Utils.getBackResId
import com.example.android.sunshine.utilities.Utils.logDBvalues
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), CardsAdapter.onItemClickListener {

    override fun onWeatherImageClicked() {
    }

    override fun onMapClicked() {
    }

    private lateinit var viewModel: MainActivityViewModel
    private var mPosition = RecyclerView.NO_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)
        val adapter = CardsAdapter(this, listOf(), this)
        recyclerView.adapter = adapter

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        InjectorUtils.provideRepository(this).initializeDataCW()
        InjectorUtils.provideRepository(this).initializeData()

        viewModel.currentWeather.observe(this, androidx.lifecycle.Observer { entry ->
            if (entry != null) {
                showRecyclerView()
                val currentWeatherUpdate = CurrentWeatherUpdate(entry)
                val detailsUpdate = DetailsUpdate(entry)
                val list = listOf(
                        currentWeatherUpdate,
                        detailsUpdate
                )
                adapter.swapForecast(list)
                Log.d(MainActivity.TAG, "DB entry CW: " + SimpleDateFormat(" dd MMM HH:mm").format(entry.date) + "  temp " + entry.temp)
//                logDBvalues(this, weatherEntries)
            } else showLoading()
        })

        viewModel.forecast.observe(this, androidx.lifecycle.Observer { listWeatherEntries ->
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0
            recyclerView.smoothScrollToPosition(mPosition)
            if (listWeatherEntries != null && listWeatherEntries.size > 0) {
                showRecyclerView()
//                TemperatureGraphCardUtils.initGraph(weatherEntries, my_graph)
                logDBvalues(this, listWeatherEntries)

//                adapter.swapForecast(listOf(wt))
            } // else showLoading()
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
        val TAG = "MainActivity"
    }

}
