package com.example.mpandroidchart

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class BarChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        val chart: BarChart = findViewById(R.id.barchart)

        val NoOfEmp: MutableList<BarEntry> = mutableListOf()

        NoOfEmp.add(BarEntry(9f, 0f))
        NoOfEmp.add(BarEntry(10f, 1f))
        NoOfEmp.add(BarEntry(11f, 2f))
        NoOfEmp.add(BarEntry(12f, 3f))
        NoOfEmp.add(BarEntry(13f, 4f))

        val NoOfEmp2: MutableList<BarEntry> = mutableListOf()

        NoOfEmp2.add(BarEntry(9f, 2f))
        NoOfEmp2.add(BarEntry(10f, 4f))
        NoOfEmp2.add(BarEntry(11f, 1f))
        NoOfEmp2.add(BarEntry(12f, 3f))
        NoOfEmp2.add(BarEntry(13f, 0f))

        val year: MutableList<String> = mutableListOf()

        year.add("2008")
        year.add("2009")
        year.add("2010")
        year.add("2011")
        year.add("2012")
        year.add("2013")
        year.add("2014")
        year.add("2015")
        year.add("2016")
        year.add("2017")

        val bardataset = BarDataSet(NoOfEmp, "No Of Employee") // object data
        val bardataset2 = BarDataSet(NoOfEmp2, "No") // object data
        bardataset2.setColors(Color.RED)
        chart.animateY(1000) // hieu ung khi chay du lieu

        /* config bar chart */
        chart.description.isEnabled = false
        chart.setDrawBarShadow(false)
        chart.legend.isEnabled = false
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)
        chart.setFitBars(false)

        chart.axisLeft.minWidth = 3f
        chart.setVisibleXRange(1f, 10f)
        chart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        chart.axisLeft.setDrawGridLines(true)
        chart.axisLeft.enableGridDashedLine(10f, 10f, 0f)
        chart.axisLeft.axisMinimum = 0f // set data start from bottom  = 0f
//        chart.axisLeft.axisMaximum = 0f
//        chart.axisRight.axisMaximum = 0f
        chart.axisRight.isEnabled = false
        chart.xAxis.setDrawGridLines(true)
        chart.xAxis.enableGridDashedLine(10f, 10f, 0f)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.axisLineColor = Color.RED
        /* **************** */

        val data = BarData(bardataset, bardataset2) // put into bar data
        chart.data = data
    }
}