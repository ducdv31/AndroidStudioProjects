package com.example.myhome.historyactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.historyactivity.HistoryActivity
import com.example.myhome.historyactivity.adapter.DhtDateHistoryAdapter
import com.example.myhome.historyactivity.model.DateHistory
import com.example.myhome.tool.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryDhtFragment : Fragment() {
    private val TAG = HistoryDhtFragment::class.java.simpleName
    private lateinit var rvHistoryDht: RecyclerView
    private lateinit var dhtDateHistoryAdapter: DhtDateHistoryAdapter
    private lateinit var historyActivity: HistoryActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_history_dht, container, false)
        rvHistoryDht = fragView.findViewById(R.id.rv_history_dht)
        historyActivity = activity as HistoryActivity
        dhtDateHistoryAdapter = DhtDateHistoryAdapter {
            /* open fragment detail */
            historyActivity.gotoFragment(DhtDetailHistoryFragment(), it, true)
        }
        historyActivity.setTitleActionBar(
            historyActivity.getString(R.string.history),
            R.drawable.outline_date_range_white_36dp
        )

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvHistoryDht.layoutManager = linearLayoutManager
        rvHistoryDht.adapter = dhtDateHistoryAdapter

        CoroutineScope(Dispatchers.IO).launch {
            getHistoryDate()
        }

        return fragView
    }

    private fun getHistoryDate() {
        FirebaseDatabase.getInstance().reference.child(Constant.DHT11_CHILD)
            .child(Constant.HISTORY_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val listDateHistory: MutableList<DateHistory> = mutableListOf()
                        /* get date */
                        for (dataSnapDate: DataSnapshot in snapshot.children) {
                            /* dataSnapDate = date */
                            listDateHistory.add(
                                DateHistory(
                                    dataSnapDate.key
                                )
                            )
                        }
                        dhtDateHistoryAdapter.setData(listDateHistory)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}