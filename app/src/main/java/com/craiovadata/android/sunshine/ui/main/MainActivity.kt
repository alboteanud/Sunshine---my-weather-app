package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.adpterModels.*
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.adpterModels.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.ui.policy.PrivacyPolicyActivity
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import com.craiovadata.android.sunshine.utilities.LogUtils
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.math.log

class MainActivity : BaseActivity(), CardsAdapter.Listener {

    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: List<ListWeatherEntry>? = null
    private var multiDayEntries: List<ListWeatherEntry>? = null
    private var webcamEntries: List<WebcamEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
    private lateinit var mAdapter: CardsAdapter
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.apply {
//            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        myViewModel = ViewModelProvider(this@MainActivity, factory).get(MyViewModel::class.java)

        observeCurrentWeather(myViewModel)
        observeDayWeather(myViewModel)
        observeDaysWeather(myViewModel)
        observeWebcamsData(myViewModel)

        lifecycle.addObserver(myViewModel)
    }

    private class MyLinearLayoutManager(context: Context) :
        LinearLayoutManager(context) {
        private var didScroll = false

        // Force new items appear at the top
        override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
            super.onItemsAdded(recyclerView, positionStart, itemCount)
//            val position = findLastCompletelyVisibleItemPosition()
            if (!didScroll)
                scrollToPosition(0)
        }

        override fun onScrollStateChanged(state: Int) {
            super.onScrollStateChanged(state)
            if (!didScroll) didScroll = true
        }

    }

    private fun observeCurrentWeather(myViewModel: MyViewModel) {
        myViewModel.currentWeatherObservable.observe(this,
            androidx.lifecycle.Observer<List<WeatherEntry>> { listEntries ->
                if (listEntries.isNullOrEmpty()) {
                    showLoading()
                    return@Observer
                }
                showRecyclerView()
                currentWeatherEntry = listEntries[0]
                updateAdapter()
                logAndWarnCurrentWeather(listEntries)
            })
    }

    private fun observeDayWeather(myViewModel: MyViewModel) {
        myViewModel.nextHoursWeatherObservable.observe(this, Observer { listEntries ->
            if (listEntries.isNullOrEmpty()) return@Observer
            showRecyclerView()
            graphWeatherEntries = listEntries
            updateAdapter()
        })
    }

    private fun observeDaysWeather(myViewModel: MyViewModel) {
        myViewModel.midDayWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries.isNullOrEmpty()) return@Observer
            multiDayEntries = listEntries
            updateAdapter()
        })
    }
  private fun observeWebcamsData(myViewModel: MyViewModel) {
        myViewModel.webcams.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries.isNullOrEmpty()) return@Observer
            webcamEntries = listEntries
//            listEntries.forEach { webcamEntry ->  LogUtils.log(webcamEntry.title) }
            updateAdapter()
        })
    }

    override fun updateAdapter() {
        val updates = mutableListOf<Base>()
//        // Primele 4 sunt notificate de schimbare °C|°F - onCelsiusFarClicked
        updates.add(
            CurrentWeather(
                currentWeatherEntry
            )
        )
        updates.add(
            Graph(
                graphWeatherEntries
            )
        )
        updates.add(
            Details(
                currentWeatherEntry
            )
        )
        updates.add(
            MultiDay(
                multiDayEntries
            )
        )
        if (!webcamEntries.isNullOrEmpty()){
            updates.add(Webcam(webcamEntries)
            )
        }
        updates.add(
            Map(
                currentWeatherEntry
            )
        )
        updates.add(News(""))
        if (adViewMedRectangle != null) updates.add(updates.size,
            Ads(adViewMedRectangle)
        )
        mAdapter.setUpdates(updates)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_privacy_policy -> {
                if (inTestMode) {
                    val pref = getSharedPreferences("_", MODE_PRIVATE)
                    val savedTxt = pref.getString(PREF_SYNC_KEY, "")
                    layoutAttention.visibility = View.VISIBLE
                    layoutAttention.textViewWarnCityWrong.text = savedTxt
                } else startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCelsiusFarClicked(view: View) {
        recyclerView.adapter?.notifyItemRangeChanged(0, 4)
    }

    override fun onNewsClicked(view: View) {
        startActivity(Intent(this, NewsActivity::class.java))
    }

    fun onOkTestButtonPressed(view: View) {
        layoutAttention.visibility = View.GONE
    }

    companion object {
        const val PREF_SYNC_KEY = "sync_key"
    }

}
