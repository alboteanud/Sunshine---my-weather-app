package com.example.android.sunshine.ui.cards

import android.graphics.Color
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils.getCityTimeMills
import com.example.android.sunshine.utilities.SunshineWeatherUtils.adaptTemperature
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.*

object TemperatureGraphCardUtils {

    fun initGraph(weatherEntries: MutableList<ListWeatherEntry>, graph1: GraphView) {
//        if (weatherEntries.size < 4) return

        val series = LineGraphSeries<DataPoint>()
        val numPoints = weatherEntries.size

        for (i in 0 until numPoints) {
            val entry = weatherEntries[i]
            val time = entry.date.time
            val cityTime = getCityTimeMills(graph1.context, time)
            val date = Date(cityTime)
            val temperature = adaptTemperature(graph1.context, entry.temp)
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
        graph1.removeAllSeries()
        graph1.addSeries(series)
        val numLabelsH = (numPoints).toInt()
        graph1.gridLabelRenderer.numHorizontalLabels = numLabelsH
//        graph1.gridLabelRenderer.horizontalAxisTitle = "Hour"
//        graph1.gridLabelRenderer.verticalAxisTitle = "Temperature"
        graph1.gridLabelRenderer.gridStyle = GridLabelRenderer.GridStyle.NONE
        graph1.gridLabelRenderer.setHumanRounding(false, true)

        graph1.viewport.isXAxisBoundsManual = true
        //            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
        val format = SimpleDateFormat("HH")
        graph1.gridLabelRenderer.labelFormatter = object : DateAsXAxisLabelFormatter(graph1.context, format) {
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
