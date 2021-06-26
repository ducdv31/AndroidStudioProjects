package com.example.swiperefreshlaayout

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var hintFaqAdapter: HintFaqAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rcv_list_hint)
        swipeRefreshLayout = findViewById(R.id.srl_main)
        hintFaqAdapter = HintFaqAdapter()

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = hintFaqAdapter

        hintFaqAdapter.setData(listData())

        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.teal_700))
    }

    private fun listData(): MutableList<Hint> {
        val list:MutableList<Hint> = mutableListOf()
        for (i in 0..5){
            list.add(Hint("Ok $i"))
        }
        return list
    }

    override fun onRefresh() {
        hintFaqAdapter.setData(listData2())
        val handler = Handler()
        handler.postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 3000)
        //swipeRefreshLayout.isRefreshing = false
    }

    private fun listData2(): MutableList<Hint> {
        val list:MutableList<Hint> = mutableListOf()
        for (i in 0..15){
            list.add(Hint("Ok $i"))
        }
        return list
    }
}