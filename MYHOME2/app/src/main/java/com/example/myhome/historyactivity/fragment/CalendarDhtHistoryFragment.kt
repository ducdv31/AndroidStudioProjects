package com.example.myhome.historyactivity.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.activitymain.model.Dht11Value
import com.example.myhome.historyactivity.HistoryActivity
import com.example.myhome.historyactivity.adapter.DhtDetailHistoryAdapter
import com.example.myhome.historyactivity.model.DhtTimeValueModel
import com.example.myhome.tool.Constant
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.time.LocalDate

class CalendarDhtHistoryFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    private lateinit var dhtDetailHistoryAdapter: DhtDetailHistoryAdapter
    private lateinit var historyActivity: HistoryActivity
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_calendar_dht_history, container, false)
        calendarView = fragView.findViewById(R.id.dht_history_calendar)
        recyclerView = fragView.findViewById(R.id.rv_detail_dht_history)
        shimmerFrameLayout = fragView.findViewById(R.id.sm_rv_detail_dht_history)
        historyActivity = activity as HistoryActivity
        dhtDetailHistoryAdapter = DhtDetailHistoryAdapter()
        historyActivity.setTitleActionBar(
            historyActivity.getString(R.string.detail),
            R.drawable.outline_analytics_white_36dp
        )
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = dhtDetailHistoryAdapter

        runShimmer(true)
        getDataFromDate(getCurrentDate())

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            runShimmer(true)
            val date = "$dayOfMonth ${getMonth(month)} $year"
            getDataFromDate(date)
        }

        return fragView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate(): String {
        val localDate: LocalDate = LocalDate.now()
        val month =
            localDate.month.toString().substring(0, 1).uppercase() + localDate.month.toString()
                .substring(1).lowercase()
        return "${localDate.dayOfMonth} $month ${localDate.year}"
    }

    private fun getDataFromDate(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            FirebaseDatabase.getInstance().reference
                .child(Constant.DHT11_CHILD).child(Constant.HISTORY_CHILD)
                .child(date).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val listData: MutableList<DhtTimeValueModel> = mutableListOf()
                        for (snapTime: DataSnapshot in snapshot.children) {
                            val dht11Value: Dht11Value? = snapTime.getValue(Dht11Value::class.java)
                            dht11Value?.let {
                                listData.add(DhtTimeValueModel(snapTime.key!!.toInt(), it))
                            }
                        }
                        dhtDetailHistoryAdapter.setData(listData)
                        runShimmer(false)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
    }

    private fun getMonth(num: Int): String {
        var month = ""
        val dfs = DateFormatSymbols()
        val months: Array<String> = dfs.months
        if (num in 0..11) {
            month = months[num]
        }
        return month
    }

    private fun runShimmer(isRun: Boolean) {
        if (isRun) {
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmerAnimation()
            recyclerView.visibility = View.GONE
        } else {
            shimmerFrameLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}