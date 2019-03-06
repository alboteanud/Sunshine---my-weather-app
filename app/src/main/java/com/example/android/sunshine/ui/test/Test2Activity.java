package com.example.android.sunshine.ui.test;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.sunshine.R;
import com.example.android.sunshine.data.database.ListWeatherEntry;
import com.example.android.sunshine.utilities.InjectorUtils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2Activity extends AppCompatActivity {
    private TestActivityViewModel mViewModel;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        GraphView graph = findViewById(R.id.graph);

        TestViewModelFactory factory = InjectorUtils.provideTestActivityViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(TestActivityViewModel.class);
        mViewModel.getForecast().observe(this, weatherEntries -> {
            if (weatherEntries == null || weatherEntries.size() == 0) return;
            Log.d("main", "Total values: " + weatherEntries.size());

            int numPoints = 5;
            for (int i = 0; numPoints > i; i++) {
                ListWeatherEntry entry = weatherEntries.get(i);
                double temperature = entry.getTemp();
                long dateInMillis = entry.getDate().getTime();
                Date date = new Date(dateInMillis);

                series.appendData(new DataPoint(date, temperature), true, numPoints);
            }
//            series.setDrawBackground(true);
            series.setAnimated(true);
            series.setDrawDataPoints(true);
            series.setTitle("Weather");
            series.setColor(Color.GREEN);

            graph.addSeries(series);
            SimpleDateFormat format = new SimpleDateFormat("HH");
//            DateAsXAxisLabelFormatter formater = new DateAsXAxisLabelFormatter(graph.getContext(), format);
//            graph.getGridLabelRenderer().setLabelFormatter(formater);
//            int numLabelsH = (int) (numPoints * 1.0);
//            graph.getGridLabelRenderer().setNumHorizontalLabels(numLabelsH);
//            graph.getViewport().setMinX(d1.getTime());
//            graph.getViewport().setMaxX(d3.getTime());
//            graph.getViewport().setXAxisBoundsManual(true);
            graph.getGridLabelRenderer().setHumanRounding(false, true);

            // custom label formatter to show currency "EUR"
//            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext(), format) {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show normal x values

                        return super.formatLabel(value, isValueX);
                    } else {
                        // show currency for y values
                        return super.formatLabel(value, isValueX) + "\u00B0";
                    }
                }
            });

        });

//        graph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");
//        graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature");

        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
//        graph.getGridLabelRenderer().setTextSize(20);
//        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
//        graph.getGridLabelRenderer().setHorizontalLabelsAngle(120);
//        graph.getGridLabelRenderer().reloadStyles();


    }
}
