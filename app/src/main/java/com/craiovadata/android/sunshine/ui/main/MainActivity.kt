package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.data.database.ListWeatherEntry
import com.craiovadata.android.sunshine.data.database.WeatherEntry
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), CardsAdapter.Listener {

    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: List<ListWeatherEntry>? = null
    private var multiDayEntries: List<ListWeatherEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
    private lateinit var mAdapter: CardsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        viewModel = ViewModelProviders.of(this@MainActivity, factory).get(MainViewModel::class.java)

        observeCurrentWeather(viewModel)
        observeNextHoursData(viewModel)
        observeDaysForecastData(viewModel)

        lifecycle.addObserver(viewModel)
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
                    logAndWarnCurrentWeather(entries)
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
            if (listEntries != null && listEntries.isNotEmpty()) {
                multiDayEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    override fun updateAdapter() {
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

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCelsiusFarClicked(view: View) {
        recyclerView.adapter?.notifyItemRangeChanged(0, 4)
    }

    override fun onNewsClicked(view: View) {
        startActivity(Intent(this, NewsActivity::class.java))
    }

}
