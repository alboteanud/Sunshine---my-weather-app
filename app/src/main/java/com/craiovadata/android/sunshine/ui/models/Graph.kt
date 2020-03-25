package com.craiovadata.android.sunshine.ui.models

import android.graphics.Color
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
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
            view.graphView.removeAllSeries()

            val series = LineGraphSeries<DataPoint>()
            entries.forEach { entry ->
                val temperature =
                    SunshineWeatherUtils.adaptTemperature(view.context, entry.temperature)
                val dataPoint = DataPoint(entry.date, temperature)
//                series.appendData(dataPoint, false, entries.size)
                series.appendData(dataPoint, true, entries.size+2, true)
            }

            series.apply {
                color = view.context.getColor(R.color.semitransparentGray)
//            backgroundColor = Color.TRANSPARENT
                isDrawBackground = true
                setAnimated(false)
                thickness = 3
                isDrawDataPoints = true
//                dataPointsRadius = 3.0f

            }

            view.graphView.apply {
//                removeAllSeries()
//                onDataChanged(false, false)

                addSeries(series)
                gridLabelRenderer.apply {
//                    numHorizontalLabels = entries.size
//                    numVerticalLabels = 3
//                horizontalAxisTitle = "hour"
//                    verticalAxisTitle = "Temperature"
//                    horizontalAxisTitle =  "\u23F0"
//                    horizontalAxisTitle =  "\uE12b"
val timeFormat = if (BuildConfig.DEBUG) { "HH:mm" }
                    else { "HH:mm" }
                    gridStyle = GridLabelRenderer.GridStyle.NONE
                    setHumanRounding(false, true)
                    labelFormatter = object :
                        DateAsXAxisLabelFormatter(context, CityData.getFormatterCityTZ(timeFormat)) {
                        override fun formatLabel(value: Double, isValueX: Boolean): String {
                            return if (isValueX) super.formatLabel(value, isValueX)
                            else super.formatLabel(value, isValueX) + "\u00B0"
                        }
                    }
                }
//                title = context.getString(R.string.title_graph_temperature)
                onDataChanged(false, false)
            }



            val cityTimeZone = CityData.getCityTimeZone()
            view.textViewClockSymbol.setOnClickListener {
                Toast.makeText(it.context, cityTimeZone.displayName, Toast.LENGTH_SHORT).show()
            }
            if (BuildConfig.DEBUG && cityTimeZone.id == "GMT") {
//                var text = cityTimeZone.displayName
                val text = "timp GMT -> TIME_ZONE_ID eroare: $cityTimeZone"
                view.textViewClockSymbol.setTextColor(Color.RED)
                view.textViewClockSymbol.text = text
            }
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

