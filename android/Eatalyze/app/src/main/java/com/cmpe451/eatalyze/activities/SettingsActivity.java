package com.cmpe451.eatalyze.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.cmpe451.eatalyze.R;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.PieChartL;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PieChartL pie = (PieChartL) findViewById(R.id.pie_chart);

        List<ChartData> values = new ArrayList<>();

        values.add(new ChartData("Cricket", 50f));
        values.add(new ChartData("Football", 60f));
        values.add(new ChartData("Hockey", 30f));
        values.add(new ChartData("Tenis", 20f));
        values.add(new ChartData("Rugby",40f));
        values.add(new ChartData("Polo",10f));
        pie.setData(values);
    }
}
