package com.example.mpandroidchart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        val pieChart: PieChart = findViewById(R.id.piechart)
        val NoOfEmp : MutableList<PieEntry> = mutableListOf()

        NoOfEmp.add(PieEntry(945f, 0f))
        NoOfEmp.add(PieEntry(1040f, 1f))
        NoOfEmp.add(PieEntry(1133f, 2f))
        NoOfEmp.add(PieEntry(1240f, 3f))
        NoOfEmp.add(PieEntry(1369f, 4f))
        NoOfEmp.add(PieEntry(1487f, 5f))
        NoOfEmp.add(PieEntry(1501f, 6f))
        NoOfEmp.add(PieEntry(1645f, 7f))
        NoOfEmp.add(PieEntry(1578f, 8f))
        NoOfEmp.add(PieEntry(1695f, 9f))
        val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

        val year : MutableList<String> = mutableListOf()

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
        val data = PieData(dataSet)
        pieChart.setData(data)
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS, this)
        pieChart.animateXY(5000, 5000)
    }
}