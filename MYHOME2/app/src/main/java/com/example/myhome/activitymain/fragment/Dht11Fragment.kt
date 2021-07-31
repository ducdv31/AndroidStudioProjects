package com.example.myhome.activitymain.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity
import com.example.myhome.activitymain.model.Dht11Value
import com.example.myhome.historyactivity.HistoryActivity
import com.example.myhome.tool.Constant
import com.example.myhome.tool.TimeConverter
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class Dht11Fragment : Fragment() {

    private val TAG = Dht11Fragment::class.java.simpleName
    private lateinit var tValue: TextView
    private lateinit var hValue: TextView
    private lateinit var layoutCurrentValue: RelativeLayout
    private lateinit var lineChart: LineChart
    private lateinit var mainActivity: MainActivity
    private lateinit var shimmerTHValue: ShimmerFrameLayout
    private lateinit var shimmerLineChart: ShimmerFrameLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_dht11, container, false)
        tValue = fragView.findViewById(R.id.t_value)
        hValue = fragView.findViewById(R.id.h_value)
        lineChart = fragView.findViewById(R.id.line_chart_dht11)
        layoutCurrentValue = fragView.findViewById(R.id.layout_current_value)
        mainActivity = activity as MainActivity
        shimmerTHValue = fragView.findViewById(R.id.sm_th_value)
        shimmerLineChart = fragView.findViewById(R.id.sm_line_chart)

        mainActivity.setActionBar(mainActivity.getString(R.string.my_home), false)
        layoutCurrentValue.setOnClickListener {
            mainActivity.gotoActivity(HistoryActivity::class.java)
        }

        shimmerTHValue.startShimmerAnimation()
        shimmerLineChart.startShimmerAnimation()

        configChart(lineChart)

        CoroutineScope(Dispatchers.IO).launch {
            getCurrentData()
        }

        CoroutineScope(Dispatchers.IO).launch {
            getDataForLineChart()
        }

        return fragView
    }

    private fun getCurrentData() {
        FirebaseDatabase.getInstance().reference.child(Constant.DHT11_CHILD)
            .child(Constant.CURRENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dht11Value: Dht11Value? = snapshot.getValue(Dht11Value::class.java)
                    setDht11View(dht11Value)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataForLineChart() {
        FirebaseDatabase.getInstance().reference
            .child(Constant.DHT11_CHILD).child(Constant.HISTORY_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (dataDate: DataSnapshot in snapshot.children) {
                            if (dataDate.key.toString() == getCurrentDate()) {
                                val tEntry: MutableList<Entry> = mutableListOf()
                                val hEntry: MutableList<Entry> = mutableListOf()
                                if (dataDate.hasChildren()) {
                                    for (dataTime: DataSnapshot in dataDate.children) {
                                        val dht11Value: Dht11Value? =
                                            dataTime.getValue(Dht11Value::class.java)
                                        dht11Value?.let {
                                            tEntry.add(
                                                Entry(
                                                    dataTime.key!!.toFloat(),
                                                    (dht11Value.t ?: 0).toFloat()
                                                )
                                            )
                                            hEntry.add(
                                                Entry(
                                                    dataTime.key!!.toFloat(),
                                                    (dht11Value.h ?: 0).toFloat()
                                                )
                                            )
                                        }
                                        show2LineChart(
                                            lineChart,
                                            tEntry, "Temperature", Color.YELLOW,
                                            hEntry, "Humidity", Color.GREEN
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun show2LineChart(
        lineChart: LineChart,
        listData1: MutableList<Entry>,
        name1: String,
        color1: Int,
        listData2: MutableList<Entry>,
        name2: String,
        color2: Int
    ) {
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return TimeConverter.convertFromMinutes(value.toInt())
            }
        }

        val lineDataSet1 = LineDataSet(listData1, name1)
        val lineDataSet2 = LineDataSet(listData2, name2)

        lineDataSet1.color = color1
        lineDataSet1.setDrawCircles(false)

        lineDataSet2.color = color2
        lineDataSet2.setDrawCircles(false)

        val iLineDataSet: MutableList<ILineDataSet> = mutableListOf()
        iLineDataSet.add(lineDataSet1)
        iLineDataSet.add(lineDataSet2)

        val lineData = LineData(iLineDataSet)

        lineChart.clear()
        lineChart.data = lineData
        lineChart.invalidate()

        shimmerLineChart.visibility = View.GONE
        lineChart.visibility = View.VISIBLE
    }

    private fun configChart(lineChart: LineChart) {
        lineChart.setPinchZoom(false)
        lineChart.setMaxVisibleValueCount(20)
        lineChart.description.isEnabled = false
        lineChart.isDoubleTapToZoomEnabled = true
        lineChart.isScaleYEnabled = false
        /* xAxis */
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.axisLineWidth = 1.1f
        /* yAxis */
        lineChart.axisRight.isEnabled = false
        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(true)
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.axisLineWidth = 1.1f
    }

    @SuppressLint("SetTextI18n")
    private fun setDht11View(dht11Value: Dht11Value?) {
        tValue.text = "${dht11Value?.t ?: 0} ºC"
        hValue.text = "${dht11Value?.h ?: 0} %"
        shimmerTHValue.stopShimmerAnimation()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate():String{
        val localDate = LocalDate.now()
        var month = localDate.month.toString()
        month = month.substring(0, 1).uppercase() + month.substring(1).lowercase()
        val day = if (localDate.dayOfMonth < 10) {
            "0${localDate.dayOfMonth}"
        } else {
            localDate.dayOfMonth
        }
        return "$day $month ${localDate.year}"
    }
}