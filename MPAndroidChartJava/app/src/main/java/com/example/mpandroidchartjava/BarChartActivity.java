package com.example.mpandroidchartjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        BarChart chart = findViewById(R.id.barchart);

        ArrayList<BarEntry> NoOfEmp = new ArrayList<BarEntry>();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
//        NoOfEmp.add(new BarEntry(1487f, 5));
//        NoOfEmp.add(new BarEntry(1501f, 6));
//        NoOfEmp.add(new BarEntry(1645f, 7));
//        NoOfEmp.add(new BarEntry(1578f, 8));
//        NoOfEmp.add(new BarEntry(1695f, 9));

        XAxis xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 2f);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceMax(0.5f);
        xAxis.setSpaceMin(0.5f);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(getResources().getColor(R.color.teal_200));

//        YAxis yAxis = chart.getAxisLeft();
//        yAxis.setEnabled(true);
//        yAxis.setAxisMinimum(1f);
//        yAxis.setAxisMaximum(20f);
//
//        yAxis.enableAxisLineDashedLine(10f, 10f, 10f);
//        yAxis.setDrawGridLines(true);
//        yAxis.setGranularityEnabled(true);
//        yAxis.enableGridDashedLine(10f, 10f, 0f);
//        yAxis.setDrawAxisLine(true);
//
//        YAxis yAxis1 = chart.getAxisLeft();
//        yAxis1.enableAxisLineDashedLine(10f, 10f, 10f);
//        yAxis1.setDrawGridLines(true);
//        yAxis1.setDrawAxisLine(true);


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        chart.animateY(5000);
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);

    }
}