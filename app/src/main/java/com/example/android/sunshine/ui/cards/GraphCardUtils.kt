package com.example.android.sunshine.ui.cards

import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.ContextCompat
import com.example.android.sunshine.data.database.ListWeatherEntry
import com.example.android.sunshine.ui.main.Adapter
import com.example.android.sunshine.utilities.SunshineWeatherUtils.adaptTemperature
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.graph_card.view.*
import java.text.SimpleDateFormat
import java.util.*




object GraphCardUtils {

    fun bindForecastToUI(weatherEntries: MutableList<ListWeatherEntry>, view: View, listener: Adapter.onItemClickListener) {
        drawGraph(weatherEntries, view.graphView)
        setTextCelsiusFarStates(view, listener)
    }

    private fun setTextCelsiusFarStates(view: View, listener: Adapter.onItemClickListener) {
        val context = view.context
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val key = context.getString(com.example.android.sunshine.R.string.pref_units_key)
        val isImperialDefault = context.resources.getBoolean(com.example.android.sunshine.R.bool.is_imperial_default)
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
        blueView.background = context.getDrawable(com.example.android.sunshine.R.drawable.selector)

        whiteView.isClickable = false
        whiteView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        whiteView.setOnClickListener(null)
        whiteView.background = null

    }

    private fun drawGraph(entries: MutableList<ListWeatherEntry>, graphView: GraphView) {
        val context = graphView.context
        val series = LineGraphSeries<DataPoint>()
        //        for (i in 0 until entries.size) { }
        entries.forEach { entry ->
//            val date = Date(getCityDate(context, entry.date.time))
//            val date = getCityDate(context, entry.date.time)
            val temperature = adaptTemperature(context, entry.temp)
            val dataPoint = DataPoint(entry.date, temperature)
            series.appendData(dataPoint, false, entries.size)
        }


        series.apply {
            color = context.getColor(com.example.android.sunshine.R.color.semitransparentWhite)
//            backgroundColor = Color.TRANSPARENT
            isDrawBackground = true
            setAnimated(true)
            thickness = 3
            isDrawDataPoints = false
        }
        graphView.apply {
            removeAllSeries()
            addSeries(series)
            gridLabelRenderer.apply {
                numHorizontalLabels = entries.size
//                horizontalAxisTitle = "Hour"
                gridStyle = GridLabelRenderer.GridStyle.NONE
                numVerticalLabels = 4
                setHumanRounding(false, true)
                val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())

                val timeZoneID = TimeZone.getAvailableIDs()[358]
                val tz_city = TimeZone.getTimeZone(timeZoneID)
                simpleDateFormat.setTimeZone(tz_city)

                labelFormatter = object : DateAsXAxisLabelFormatter(context, simpleDateFormat) {
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
    }


}
