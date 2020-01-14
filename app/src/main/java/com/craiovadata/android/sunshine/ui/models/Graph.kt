package com.craiovadata.android.sunshine.ui.models

import android.graphics.Color
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.main.CardsAdapter
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import com.craiovadata.android.sunshine.CityData
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.graph_card.view.*

//(val weatherId: Int, val date: Date, val temperature: Double, val iconCodeOWM: String)
data class Graph(val list: List<ListWeatherEntry>?) :
    Base(list?.get(0)?.id, TYPE.GRAPH, list?.get(0)?.date) {

    companion object {

        @JvmStatic
        fun bindForecastToUI(
            weatherEntries: List<ListWeatherEntry>?,
            view: View,
            listener: CardsAdapter.Listener
        ) {
            if (weatherEntries == null || weatherEntries.isEmpty()) return
            drawGraph(weatherEntries, view)
            setTextCelsiusFarStates(view, listener)
        }

        private fun drawGraph(entries: List<ListWeatherEntry>, view: View) {
            val series = LineGraphSeries<DataPoint>()

            entries.forEach { entry ->
                val temperature =
                    SunshineWeatherUtils.adaptTemperature(view.context, entry.temperature)
                val dataPoint = DataPoint(entry.date, temperature)
                series.appendData(dataPoint, false, entries.size)
            }

            series.apply {
                color = view.context.getColor(R.color.semitransparentGray)
//            backgroundColor = Color.TRANSPARENT
                isDrawBackground = true
                setAnimated(true)
                thickness = 3
                isDrawDataPoints = false
            }

            view.graphView.apply {
                removeAllSeries()

                addSeries(series)
                gridLabelRenderer.apply {
                    numHorizontalLabels = entries.size
                    numVerticalLabels = 3
//                horizontalAxisTitle = "Hour"
                    gridStyle = GridLabelRenderer.GridStyle.NONE
//                    numVerticalLabels = 4
                    setHumanRounding(false, true)
                    labelFormatter = object :
                        DateAsXAxisLabelFormatter(context, CityData.getFormatterCityTZ("HH")) {
                        override fun formatLabel(value: Double, isValueX: Boolean): String {
                            return if (isValueX) super.formatLabel(value, isValueX)
                            else super.formatLabel(value, isValueX) + "\u00B0"
                        }
                    }
                }
                title = context.getString(R.string.title_graph_temperature)
//                onDataChanged(false, false)
            }


            setLabelTime(view)
        }

        private fun setLabelTime(view: View) {
            val cityTimeZone = CityData.getCityTimeZone()
            var text = cityTimeZone.displayName

            if (BuildConfig.DEBUG && cityTimeZone.id == "GMT") {
                text = "TIME_ZONE_ID error: $cityTimeZone"
                view.textLabel.setTextColor(Color.RED)
            }
            view.textLabel.text = text

        }

        private fun setTextCelsiusFarStates(view: View, listener: CardsAdapter.Listener) {
            val context = view.context
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            val key = context.getString(R.string.pref_units_key)
            val isImperialDefault = context.resources.getBoolean(R.bool.is_imperial_default)
            val isImperial = sp.getBoolean(key, isImperialDefault)

            var blueView = view.textViewFarenheit
            var whiteView = view.textViewCelsius

            if (isImperial) {
                blueView = view.textViewCelsius
                whiteView = view.textViewFarenheit
            }

            blueView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
            blueView.setOnClickListener {
                sp.edit().putBoolean(key, !isImperial).apply()
                listener.onCelsiusFarClicked(it)
            }
            blueView.isClickable = true
            blueView.background = context.getDrawable(R.drawable.selector_transp)

            whiteView.isClickable = false
            whiteView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
            whiteView.setOnClickListener(null)
            whiteView.background = null

        }

    }

}

