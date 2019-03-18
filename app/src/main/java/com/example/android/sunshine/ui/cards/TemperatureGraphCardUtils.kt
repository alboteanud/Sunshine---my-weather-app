package com.example.android.sunshine.ui.cards

import android.graphics.Color
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat
import com.example.android.sunshine.R
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils.getCityTimeMills
import com.example.android.sunshine.utilities.SunshineWeatherUtils.adaptTemperature
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.graph_card.view.*
import java.text.SimpleDateFormat
import java.util.*

object TemperatureGraphCardUtils {

    fun bindForecastToUI(weatherEntries: MutableList<ListWeatherEntry>, view: View) {
        drawGraph(weatherEntries, view)
        setTextCelsiusFarStates(view, weatherEntries)


    }

    private fun setTextCelsiusFarStates(view: View, weatherEntries: MutableList<ListWeatherEntry>) {
        val sp = PreferenceManager.getDefaultSharedPreferences(view.context)
        val key = view.context.getString(R.string.pref_units_key)
        val defaultImperial = view.context.resources.getBoolean(R.bool.default_units_imperial)
        var isSavedImperial = sp.getBoolean(key, defaultImperial)

        val txtViewCelsius = view.textViewCelsius
        val txtViewFarenheit = view.textViewFarenheit
        if (isSavedImperial) {
            txtViewCelsius.setTextColor(ContextCompat.getColor(view.context, android.R.color.holo_blue_dark))
            txtViewFarenheit.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
            txtViewCelsius.setOnClickListener {
                setTextCelsiusFarStates(view, weatherEntries)
                drawGraph(weatherEntries, view)
                isSavedImperial = !isSavedImperial
                sp.edit().putBoolean(key, isSavedImperial).apply()
            }
            txtViewFarenheit.setOnClickListener(null)
        } else {
            txtViewCelsius.setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
            txtViewFarenheit.setTextColor(ContextCompat.getColor(view.context, android.R.color.holo_blue_dark))
            txtViewFarenheit.setOnClickListener {
                setTextCelsiusFarStates(view, weatherEntries)
                drawGraph(weatherEntries, view)
                isSavedImperial = !isSavedImperial
                sp.edit().putBoolean(key, isSavedImperial).apply()
            }
            txtViewCelsius.setOnClickListener(null)

        }
    }

    private fun drawGraph(weatherEntries: MutableList<ListWeatherEntry>, view: View) {

        val series = LineGraphSeries<DataPoint>()
        val numPoints = weatherEntries.size

        val graphView = view.my_graph

        for (i in 0 until numPoints) {
            val entry = weatherEntries[i]
            val time = entry.date.time
            val cityTime = getCityTimeMills(graphView.context, time)
            val date = Date(cityTime)
            val temperature = adaptTemperature(graphView.context, entry.temp)
            val dataPoint = DataPoint(date, temperature)
            series.appendData(dataPoint, true, numPoints)
        }
        series.apply {
            //            isDrawBackground = true
            isDrawDataPoints = true
//            title = "Weather"
            color = Color.WHITE
            backgroundColor = Color.TRANSPARENT


        }
        graphView.removeAllSeries()
        graphView.addSeries(series)
        val numLabelsH = (numPoints).toInt()
        graphView.gridLabelRenderer.numHorizontalLabels = numLabelsH
//        graph1.gridLabelRenderer.horizontalAxisTitle = "Hour"
//        graph1.gridLabelRenderer.verticalAxisTitle = "Temperature"
        graphView.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        graphView.gridLabelRenderer.setHumanRounding(false, true)

        graphView.viewport.isXAxisBoundsManual = true
        //            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
        val format = SimpleDateFormat("HH")
        graphView.gridLabelRenderer.labelFormatter = object : DateAsXAxisLabelFormatter(graphView.context, format) {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    super.formatLabel(value, isValueX)
                } else {
                    super.formatLabel(value, isValueX) + "\u00B0"
                }
            }
        }
    }


}
