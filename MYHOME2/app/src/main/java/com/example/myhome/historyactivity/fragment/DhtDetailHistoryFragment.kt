package com.example.myhome.historyactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.activitymain.model.Dht11Value
import com.example.myhome.historyactivity.HistoryActivity
import com.example.myhome.historyactivity.adapter.DhtDetailHistoryAdapter
import com.example.myhome.historyactivity.model.DateHistory
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

class DhtDetailHistoryFragment : Fragment() {

    private val TAG = DhtDetailHistoryFragment::class.java.simpleName

    private lateinit var date: TextView
    private lateinit var rvDataHistory: RecyclerView
    private lateinit var dhtDetailHistoryAdapter: DhtDetailHistoryAdapter
    private lateinit var historyActivity: HistoryActivity
    private lateinit var shimmerDhtDetail: ShimmerFrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_dht_detail_history, container, false)
        date = fragView.findViewById(R.id.tv_date_in_detail_dht_history)
        rvDataHistory = fragView.findViewById(R.id.rv_detail_dht_history)
        shimmerDhtDetail = fragView.findViewById(R.id.sm_rv_detail_dht_history)
        historyActivity = activity as HistoryActivity
        dhtDetailHistoryAdapter = DhtDetailHistoryAdapter()

        shimmerDhtDetail.startShimmerAnimation()
        historyActivity.setTitleActionBar(
            historyActivity.getString(R.string.detail),
            R.drawable.outline_analytics_white_36dp
        )

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvDataHistory.layoutManager = linearLayoutManager
        rvDataHistory.adapter = dhtDetailHistoryAdapter

        getDateFromArgs()?.let {
            date.text = it.getDateFormatted()
            CoroutineScope(Dispatchers.IO).launch {
                getDataDetail(it, dhtDetailHistoryAdapter)
            }
        }

        return fragView
    }

    private fun getDateFromArgs(): DateHistory? {
        var date: DateHistory? = null
        val bundle: Bundle? = arguments
        bundle?.let {
            date = it.get(HistoryActivity.DATA_DATE_ARGS) as DateHistory
        }
        return date
    }

    private fun getDataDetail(
        dateHistory: DateHistory,
        dhtDetailHistoryAdapter: DhtDetailHistoryAdapter
    ) {
        dateHistory.date?.let {
            FirebaseDatabase.getInstance().reference
                .child(Constant.DHT11_CHILD).child(Constant.HISTORY_CHILD)
                .child(dateHistory.date).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChildren()) {
                            val listTimeData: MutableList<DhtTimeValueModel> = mutableListOf()
                            for (snapTime: DataSnapshot in snapshot.children) {
                                val dht11Value: Dht11Value? =
                                    snapTime.getValue(Dht11Value::class.java)
                                listTimeData.add(
                                    DhtTimeValueModel(
                                        snapTime.key!!.toInt(),
                                        dht11Value
                                    )
                                )
                            }
                            dhtDetailHistoryAdapter.setData(listTimeData)
                            shimmerDhtDetail.visibility = View.GONE
                            rvDataHistory.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(historyActivity, "No data", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }

    }
}