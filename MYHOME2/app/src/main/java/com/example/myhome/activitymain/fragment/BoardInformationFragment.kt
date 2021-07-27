package com.example.myhome.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity
import com.example.myhome.activitymain.model.EspInfoModel
import com.example.myhome.activitymain.model.InterTempModel
import com.example.myhome.tool.Constant
import com.example.myhome.tool.TimeConverter
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

class BoardInformationFragment : Fragment() {

    private lateinit var wifiName: TextView
    private lateinit var ip: TextView
    private lateinit var gateway: TextView
    private lateinit var subnet: TextView
    private lateinit var back: Button
    private lateinit var mainActivity: MainActivity
    private lateinit var tempInternal: TextView
    private lateinit var chart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_board_information, container, false)
        wifiName = fragView.findViewById(R.id.wifi_name)
        ip = fragView.findViewById(R.id.ip_address)
        gateway = fragView.findViewById(R.id.gateway)
        subnet = fragView.findViewById(R.id.subnet)
        back = fragView.findViewById(R.id.btn_back)
        mainActivity = activity as MainActivity
        tempInternal = fragView.findViewById(R.id.temp_internal)
        chart = fragView.findViewById(R.id.chart_temperature_internal)

        mainActivity.setActionBar(getString(R.string.board_information), false)

        back.setOnClickListener {
            mainActivity.onBackPressed()
        }

        getData()
        CoroutineScope(Dispatchers.Main).launch {
            getITemp(tempInternal)
        }

        CoroutineScope(Dispatchers.IO).launch {
            getDataForChart(chart)
        }


        return fragView
    }

    private fun getDataForChart(chart: LineChart) {
        FirebaseDatabase.getInstance().reference
            .child(Constant.I_TEMP_CHILD).child(Constant.HISTORY_CHILD)
            .limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (snapDate: DataSnapshot in snapshot.children) {
                            val listInterTemp: MutableList<Entry> = mutableListOf()
                            for (snapTime: DataSnapshot in snapDate.children) {
                                val interTempModel: InterTempModel? =
                                    snapTime.getValue(InterTempModel::class.java)
                                interTempModel?.let {
                                    listInterTemp.add(
                                        Entry(
                                            snapTime.key!!.toFloat(),
                                            interTempModel.itemp!!.toFloat()
                                        )
                                    )
                                }
                            }
                            setDataToChart(chart, listInterTemp)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun setDataToChart(chart: LineChart, listInterTemp: MutableList<Entry>) {
        /* config chart */
        chart.setPinchZoom(false)
        chart.isScaleYEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.axisLineWidth = 1.1f
        chart.xAxis.setDrawGridLines(true)
        chart.xAxis.enableGridDashedLine(10f, 10f, 0f)
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return TimeConverter.convertFromMinutes(value.toInt())
            }
        }
        chart.axisLeft.axisLineWidth = 1.1f
        chart.axisLeft.setDrawGridLines(true)
        chart.axisLeft.enableGridDashedLine(10f, 10f, 0f)
        /* ************ */
        val iTempData =
            LineDataSet(listInterTemp, getString(R.string.internal_temperature))
        iTempData.setDrawCircles(false)

        val iLineDataSet: MutableList<ILineDataSet> = mutableListOf()
        iLineDataSet.add(iTempData)

        val lineData = LineData(iLineDataSet)

        chart.clear()
        chart.data = lineData
    }

    private fun getITemp(textView: TextView) {
        FirebaseDatabase.getInstance().reference
            .child(Constant.I_TEMP_CHILD).child(Constant.CURRENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val interTempModel: InterTempModel? =
                        snapshot.getValue(InterTempModel::class.java)
                    textView.text = interTempModel?.let {
                        "${interTempModel.itemp} ºC"
                    } ?: "No data"
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity.setActionBar(mainActivity.getString(R.string.my_home), false)
    }

    private fun getData() {
        FirebaseDatabase.getInstance().reference
            .child(Constant.ESP_INFOR_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val espInfoModel: EspInfoModel? = snapshot.getValue(EspInfoModel::class.java)
                    espInfoModel?.let {
                        wifiName.text = it.WiFi
                        ip.text = it.IP
                        gateway.text = it.Gateway
                        subnet.text = it.Subnet
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}