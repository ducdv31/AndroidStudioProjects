package com.example.myhome.ui.view.activity.history

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.data.model.dht.DhtTimeValueModel
import com.example.myhome.data.model.dht.ThValue
import com.example.myhome.ui.adapter.history.DataHistoryAdapter
import com.example.myhome.utils.Constants
import com.example.myhome.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryDataActivity : BaseActivity() {
    private var sensorName: String? = null
    private lateinit var dataHistoryAdapter: DataHistoryAdapter<DhtTimeValueModel>
    private lateinit var progress_bar: ProgressBar
    private lateinit var rv_history_data: RecyclerView
    private lateinit var calendar_history: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_data)
        setShowUserImg(false)
        setTitleActionBar(getString(R.string.history))
        progress_bar = findViewById(R.id.progress_bar)
        rv_history_data = findViewById(R.id.rv_history_data)
        calendar_history = findViewById(R.id.calendar_history)

        dataHistoryAdapter = DataHistoryAdapter(this, Constants.ITEM_TYPE_DHT)
        val llmn = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_history_data.layoutManager = llmn
        rv_history_data.adapter = dataHistoryAdapter

        /* get data from previous activity */
        sensorName = intent?.extras?.get(Constants.NAME_SENSOR) as? String

        sensorName?.let {
            getDataFromDate(it, Utils.getCurrentDate())
        }

        calendar_history.setOnDateChangeListener { view, year, month, dayOfMonth ->
            sensorName?.let {
                val day = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth
                }
                val date = "$day ${Utils.getMonth(month)} $year"
                getDataFromDate(it, date)
            }
        }
    }

    private fun getDataFromDate(sensorName: String, date: String) {
        progress_bar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            FirebaseDatabase.getInstance().reference
                .child(sensorName).child(Constants.HISTORY_CHILD)
                .child(date).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val listData: MutableList<DhtTimeValueModel> = mutableListOf()
                        for (snapTime: DataSnapshot in snapshot.children) {
                            val dht11Value: ThValue? = snapTime.getValue(ThValue::class.java)
                            dht11Value?.let {
                                listData.add(DhtTimeValueModel(snapTime.key!!.toInt(), it))
                            }
                        }
                        dataHistoryAdapter.setData(listData)
                        progress_bar.visibility = View.GONE
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
    }
}