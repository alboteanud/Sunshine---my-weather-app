package com.example.android.sunshine.ui.list

import android.graphics.Color
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.*

object GraphCardUtils {

    fun initGraph(weatherEntries: MutableList<ListWeatherEntry>, graph1: GraphView) {
        val series = LineGraphSeries<DataPoint>()
        val numPoints = 5

        weatherEntries.forEachIndexed { i, entry ->
            if (i > numPoints) return@forEachIndexed
            val date = Date(entry.date.time)
            series.appendData(DataPoint(date, entry.temp), true, numPoints)
        }
        series.apply {
            isDrawBackground = true
//                    .setAnimated(true)
            isDrawDataPoints = true
            title = "Weather"
            color = Color.GREEN
            dataPointsRadius = 8f
            thickness = 6
            backgroundColor = Color.TRANSPARENT
        }
        graph1.removeAllSeries()
        graph1.addSeries(series)
//            DateAsXAxisLabelFormatter formater = new DateAsXAxisLabelFormatter(graph.getContext(), format);
//            graph.getGridLabelRenderer().setLabelFormatter(formater);
//            int numLabelsH = (int) (numPoints * 1.0);
//            graph.getGridLabelRenderer().setNumHorizontalLabels(numLabelsH);
//            graph.getViewport().setMinX(d1.getTime());
//            graph.getViewport().setMaxX(d3.getTime());
//            graph.getViewport().setXAxisBoundsManual(true);
        //            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
        graph1.gridLabelRenderer.setHumanRounding(true)
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
