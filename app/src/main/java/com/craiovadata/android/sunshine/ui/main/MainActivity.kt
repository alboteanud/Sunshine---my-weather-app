package com.craiovadata.android.sunshine.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.data.network.NetworkDataSource
import com.craiovadata.android.sunshine.ui.models.*
import com.craiovadata.android.sunshine.ui.models.Map
import com.craiovadata.android.sunshine.ui.news.NewsActivity
import com.craiovadata.android.sunshine.ui.settings.SettingsActivity
import com.craiovadata.android.sunshine.utilities.InjectorUtils
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*


class MainActivity : BaseActivity(), CardsAdapter.Listener {

    private var currentWeatherEntry: WeatherEntry? = null
    private var graphWeatherEntries: List<ListWeatherEntry>? = null
    private var multiDayEntries: List<ListWeatherEntry>? = null
    //    private var listPosition = RecyclerView.NO_POSITION
    private lateinit var mAdapter: CardsAdapter
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = MyLinearLayoutManager(this@MainActivity)

            mAdapter = CardsAdapter(this@MainActivity, listOf(), this@MainActivity)
            adapter = mAdapter
        }

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        myViewModel = ViewModelProviders.of(this@MainActivity, factory).get(MyViewModel::class.java)

        observeCurrentWeather(myViewModel)
        observeNextHoursData(myViewModel)
        observeDaysForecastData(myViewModel)

        lifecycle.addObserver(myViewModel)
    }


    private class MyLinearLayoutManager(private val context: Context) :
        LinearLayoutManager(context) {

        private var didScroll: Boolean = false

        // Force new items appear at the top
        override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
            super.onItemsAdded(recyclerView, positionStart, itemCount)
            val position = findLastCompletelyVisibleItemPosition()
            Log.d(
                TAG,
                "onItemsAdded()  positionStart: " + positionStart + " itemCount: " + itemCount +
                        "  LastCompletelyVisibleItemPosition: " + position
            )
            if (!didScroll)
                scrollToPosition(0)
        }

        override fun onScrollStateChanged(state: Int) {
            super.onScrollStateChanged(state)
            Log.d(TAG, "onScrollStateChanged()  state: $state")
            didScroll = true
        }

    }

    private fun observeCurrentWeather(myViewModel: MyViewModel) {
        myViewModel.currentWeatherObservable.observe(this,
            androidx.lifecycle.Observer<List<WeatherEntry>> { entries ->
                if (entries != null && entries.isNotEmpty()) {
                    showRecyclerView()
                    currentWeatherEntry = entries[0]
                    updateAdapter()
                    logAndWarnCurrentWeather(entries)
                } else showLoading()
            })
    }

    private fun observeNextHoursData(myViewModel: MyViewModel) {
        myViewModel.nextHoursWeatherObservable.observe(this, Observer { listEntries ->
            if (listEntries != null && listEntries.isNotEmpty()) {
                showRecyclerView()
                graphWeatherEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    private fun observeDaysForecastData(myViewModel: MyViewModel) {
        myViewModel.midDayWeather.observe(this, androidx.lifecycle.Observer { listEntries ->
            if (listEntries != null && listEntries.isNotEmpty()) {
                multiDayEntries = listEntries
                updateAdapter()
//                logDBvalues(this, listEntries, mutableListOf())
            }
        })
    }

    override fun updateAdapter() {
        val updates = mutableListOf<Base>()
//        // Primele 4 sunt notificate de schimbare °C|°F - onCelsiusFarClicked
        updates.add(CurrentWeather(currentWeatherEntry))
        updates.add(Graph(graphWeatherEntries))
        updates.add(Details(currentWeatherEntry))
        updates.add(MultiDay(multiDayEntries))
        updates.add(Map(currentWeatherEntry))
        updates.add(News(""))
        if (adViewMedRectangle != null) updates.add(updates.size, Ads(adViewMedRectangle))
        mAdapter.setUpdates(updates)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //    var iconCodeIndex = 0
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                if (inTestMode) {
                    layoutAttention.visibility = View.VISIBLE
                    layoutAttention.textViewWarnCityWrong.text = "?"

                } else
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.action_privacy_policy -> {
                if (inTestMode) {
                    // show sync times and notif times
                    val pref = PreferenceManager.getDefaultSharedPreferences(this)
                    var savedTxt = pref.getString(PREF_SYNC_KEY, "")
                    layoutAttention.visibility = View.VISIBLE


                    val workInfo = WorkManager.getInstance(this).getWorkInfosByTag(NetworkDataSource.TAG_WORK_NAME)
                    if (workInfo != null && workInfo.isCancelled) {
                        savedTxt += "wk-canceled+$workInfo"
                    }
                    if (workInfo != null && workInfo.isDone) {
                        savedTxt += "wk-done+$workInfo"
                    }


                    layoutAttention.textViewWarnCityWrong.text = savedTxt
                    // make a request for multiple cities weather - for translation purpose
//                    if (citiesIndexIncrement > 20)
//                        handler.removeCallbacksAndMessages(null)
//                    else{
//                    private val timedTask: Runnable = object : Runnable {
//                        override fun run() {
//                            if (citiIndexStart + citiesIndexIncrement < CityIds.cityIds2.size - 21) {
//                                myViewModel.forceSyncWeather(citiIndexStart + citiesIndexIncrement)
//                                citiesIndexIncrement += 20
//                                handler.postDelayed(this, 6 * 1000)
//                            }
//
//                        }
//                    }
//                        handler.post(timedTask)
//                        Toast.makeText(this, "getting data from multiple cities", Toast.LENGTH_LONG).show()
//                    }


                } else goToPrivacyPolicy()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    val citiIndexStart = 0
    var citiesIndexIncrement = 0
    val handler = Handler()


    companion object {
        const val TAG = "MainActivity"
        const val PREF_SYNC_KEY = "sync_key"
       const val languageParamMultipleCitiesTest = "es"
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

}
