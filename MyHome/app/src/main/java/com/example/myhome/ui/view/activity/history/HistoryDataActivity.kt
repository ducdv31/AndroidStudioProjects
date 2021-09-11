package com.example.myhome.ui.view.activity.history

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.data.model.dht.DhtTimeValueModel
import com.example.myhome.data.model.dht.ThValue
import com.example.myhome.ui.adapter.history.DataHistoryAdapter
import com.example.myhome.ui.view.dialog.DialogQuestion
import com.example.myhome.utils.Constants
import com.example.myhome.utils.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

class HistoryDataActivity : BaseActivity() {
    private var sensorName: String? = null
    private lateinit var dataHistoryAdapter: DataHistoryAdapter<DhtTimeValueModel>
    private lateinit var progressBar: ProgressBar
    private lateinit var rvHistoryData: RecyclerView
    private lateinit var calendarHistory: CalendarView
    private var daySelected: String = Constants.EMPTY
    private lateinit var dialogQuestion: DialogQuestion
    private lateinit var rlDataHistory: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_data)
        isShowUserImg(false)
        setTitleActionBar(getString(R.string.history))
        rlDataHistory = findViewById(R.id.rl_main_data_history)
        progressBar = findViewById(R.id.progress_bar)
        rvHistoryData = findViewById(R.id.rv_history_data)
        calendarHistory = findViewById(R.id.calendar_history)

        dialogQuestion = DialogQuestion(
            getString(R.string.title_dialog_clear_data),
            object : DialogQuestion.IClickDialogButton {
                override fun onClickCancel() {

                }

                override fun onClickOk() {
                    val bundle: Bundle? = dialogQuestion.arguments
                    var data: Any? = null
                    bundle?.let {
                        data = it[Constants.BD_DATA]
                    }
                    data?.let {
                        when (it) {
                            is DhtTimeValueModel -> {
                                clearData(Constants.DHT11_CHILD, daySelected, it.time.toString())
                            }
                        }
                    }
                }

            })
        dataHistoryAdapter = DataHistoryAdapter(
            this,
            Constants.ITEM_TYPE_DHT,
            object : DataHistoryAdapter.IClickItemData {
                override fun onClickDeleteData(data: Any) {
                    if (getStringTypeUser() == Constants.MASTER) {
                        if (!dialogQuestion.isAdded) {
                            val bundle = Bundle()
                            bundle.putSerializable(Constants.BD_DATA, data as Serializable)
                            dialogQuestion.arguments = bundle
                            dialogQuestion.show(
                                supportFragmentManager,
                                getString(R.string.title_dialog_clear_data)
                            )
                        }
                    } else {
                        Snackbar.make(
                            rlDataHistory,
                            "${Constants.CANT_DELETE_HISTORY_DATA} - cid: ${typeUserViewModel.getTypeUser().value}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                }

            })
        val llmn = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistoryData.layoutManager = llmn
        rvHistoryData.adapter = dataHistoryAdapter

        /* get data from previous activity */
        sensorName = intent?.extras?.get(Constants.NAME_SENSOR) as? String

        sensorName?.let {
            daySelected = Utils.getCurrentDate()
            getDataFromDate(it, Utils.getCurrentDate())
        }

        calendarHistory.setOnDateChangeListener { view, year, month, dayOfMonth ->
            sensorName?.let {
                val day = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    dayOfMonth
                }
                val date = "$day ${Utils.getMonth(month)} $year"
                daySelected = date
                getDataFromDate(it, date)
            }
        }
    }

    private fun getDataFromDate(sensorName: String, date: String) {
        progressBar.visibility = View.VISIBLE
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
                        progressBar.visibility = View.GONE
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
    }
}