package com.example.bluetooth.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity
import com.example.bluetooth.activitymain.adapter.RcvDataAdapter
import com.example.bluetooth.activitymain.model.DataRS
import kotlinx.coroutines.ExperimentalCoroutinesApi

class HomeFragment : Fragment() {

    lateinit var send: Button
    lateinit var contextSend: EditText
    lateinit var mainActivity: MainActivity

    /* recycler view */
    /* ************* */

    companion object {
        var listRS: MutableList<DataRS> = mutableListOf()
        var recyclerView: RecyclerView? = null
        var rcvDataAdapter: RcvDataAdapter? = null
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_home, container, false)

        mainActivity = activity as MainActivity
        mainActivity.actionBar?.title = "Bluetooth"

        send = fragView.findViewById(R.id.bt_send_bt)
        contextSend = fragView.findViewById(R.id.context_send)
        recyclerView = fragView.findViewById(R.id.rcv_list_data)
        rcvDataAdapter = RcvDataAdapter()

        MainActivity.devicesItem?.isEnabled = true

        val linearLayoutManager = LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false)

        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.adapter = rcvDataAdapter

        send.setOnClickListener {
            val data = contextSend.text.toString()
            mainActivity.sendData(data.trim())
            contextSend.setText("")
        }

        return fragView
    }

    override fun onPause() {
        super.onPause()
        mainActivity.closeKeyboard()
    }
}