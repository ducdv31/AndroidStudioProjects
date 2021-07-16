package com.example.mpandroidchart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieDataSet


class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        val pieChart: PieChart = findViewById(R.id.piechart)
        val NoOfEmp = ArrayList<Any>()

        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(945f, 0))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1040f, 1))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1133f, 2))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1240f, 3))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1369f, 4))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1487f, 5))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1501f, 6))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1645f, 7))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1578f, 8))
        NoOfEmp.add(MutableMap.MutableEntry<Any?, Any?>(1695f, 9))
        val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

        val year = ArrayList<Any>()

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
        val data = PieData(year, dataSet)
        pieChart.setData(data)
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        pieChart.animateXY(5000, 5000)
    }
}