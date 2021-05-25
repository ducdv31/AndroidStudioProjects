package com.example.bluetooth.activitymain.fragment

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity
import com.example.bluetooth.activitymain.adapter.RcvListDevicesAdapter

class ListDevicesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var rcvListDevicesAdapter: RcvListDevicesAdapter
    lateinit var mainActivity: MainActivity
    lateinit var back:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_list_devices, container, false)

        mainActivity = activity as MainActivity
        recyclerView = fragView.findViewById(R.id.rcv_list_devices)
        back = fragView.findViewById(R.id.back_to_home)

        rcvListDevicesAdapter = RcvListDevicesAdapter(object : RcvListDevicesAdapter.IClickDevice {
            override fun onClickDevice(bluetoothDevice: BluetoothDevice) {
                mainActivity.startConnect(bluetoothDevice)
            }
        })

        back.setOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }

        val linearLayoutManager = LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvListDevicesAdapter

        rcvListDevicesAdapter.setData(mainActivity.getListDevices())

        return fragView
    }

}