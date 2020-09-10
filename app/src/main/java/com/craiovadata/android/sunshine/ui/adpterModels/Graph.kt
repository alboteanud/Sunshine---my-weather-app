package com.craiovadata.android.sunshine.ui.adpterModels

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.craiovadata.android.sunshine.BuildConfig
import com.craiovadata.android.sunshine.R
import com.craiovadata.android.sunshine.ui.main.CardsAdapter
import com.craiovadata.android.sunshine.utilities.SunshineWeatherUtils
import com.craiovadata.android.sunshine.CityData
import com.craiovadata.android.sunshine.CityData.IS_IMPERIAL_UNITS_DEFAULT
import com.craiovadata.android.sunshine.CityData.inTestMode
import com.craiovadata.android.sunshine.ui.models.ListWeatherEntry
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.graph_card.view.*
import java.util.*

//(val weatherId: Int, val date: Date, val temperature: Double, val iconCodeOWM: String)
data class Graph(val list: List<ListWeatherEntry>?) :
    Base(
//        list?.get(0)?.date?.time,
        TYPE.GRAPH) {

    companion object {

        @JvmStatic
        fun bindForecastToUI(
            weatherEntries: List<ListWeatherEntry>?,
            view: View,
            listener: CardsAdapter.Listener
        ) {
            if (weatherEntries == null || weatherEntries.isEmpty()) return
            drawGraph(
                weatherEntries,
                view
            )
            setTextCelsiusFarStates(
                view,
                listener
            )
        }


        private fun drawGraph(entries: List<ListWeatherEntry>, view: View) {
            view.graphView.removeAllSeries()

            val series = LineGraphSeries<DataPoint>()
//            var minTemp = 125.0
//            var maxTemp = - 50.0
            entries.forEach { entry ->
                val temperature =
                    SunshineWeatherUtils.adaptTemperature(view.context, entry.temperature)

//               if ( temperature < minTemp) minTemp = temperature
//               if (temperature > maxTemp) maxTemp =  temperature

                val dataPoint = DataPoint(entry.date, temperature)
//                series.appendData(dataPoint, false, entries.size)
                series.appendData(dataPoint, true, entries.size + 2, true)
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

                //                title = context.getString(R.string.title_graph_temperature)
                onDataChanged(false, false)

                // set manual x bounds to have nice steps
//                viewport.isYAxisBoundsManual = true
//                viewport.setMinY(minTemp - 3)
//                viewport.setMaxY(maxTemp + 3)

                addSeries(series)
                gridLabelRenderer.apply {
//                    numHorizontalLabels = entries.size
//                   numVerticalLabels = ((maxTemp - minTemp)/6).toInt() + 3
//numVerticalLabels = 4
//                horizontalAxisTitle = "hour"
//                    verticalAxisTitle = "Temperature"
//                    horizontalAxisTitle =  "\u23F0"
//                    horizontalAxisTitle =  "\uE12b"

                    gridStyle = GridLabelRenderer.GridStyle.NONE
                    setHumanRounding(false, true)
                    labelFormatter = object :
                        DateAsXAxisLabelFormatter(
                            context,
                            CityData.getFormatterCityTZ("HH:mm")
                        ) {
                        override fun formatLabel(value: Double, isValueX: Boolean): String {
                            return if (isValueX) super.formatLabel(value, isValueX)
                            else super.formatLabel(value, isValueX) + "\u00B0"
                        }
                    }
                }

            }

            val cityTimeZone = TimeZone.getTimeZone(CityData.TIME_ZONE_ID)
            view.textViewClockSymbol.setOnClickListener {
                Toast.makeText(it.context, cityTimeZone.displayName, Toast.LENGTH_SHORT).show()
            }
            if (inTestMode && cityTimeZone.id == "GMT") {
//                var text = cityTimeZone.displayName
                val text = "GMT time -> TIME_ZONE_ID error: $cityTimeZone"
                view.textViewClockSymbol.setTextColor(Color.RED)
                view.textViewClockSymbol.text = text
            }
        }

        private fun setTextCelsiusFarStates(view: View, listener: CardsAdapter.Listener) {
            val context = view.context

            val sp = getDefaultSharedPreferences(context)
            val key = context.getString(R.string.pref_units_key)
            val isImperial = sp.getBoolean(key, IS_IMPERIAL_UNITS_DEFAULT)

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

