package com.example.bluetooth.activitymain.fragment

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity
import com.example.bluetooth.activitymain.adapter.RcvListDevicesAdapter
import java.util.*

class ListDevicesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var rcvListDevicesAdapter: RcvListDevicesAdapter
    lateinit var mainActivity: MainActivity
    private lateinit var back: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_list_devices, container, false)

        mainActivity = activity as MainActivity
        mainActivity.actionBar?.title = "Devices connected"

        recyclerView = fragView.findViewById(R.id.rcv_list_devices)
        back = fragView.findViewById(R.id.back_to_home)
        MainActivity.devicesItem!!.isEnabled = false

        rcvListDevicesAdapter = RcvListDevicesAdapter(object : RcvListDevicesAdapter.IClickDevice {
            override fun onClickDevice(bluetoothDevice: BluetoothDevice) {
                mainActivity.startConnect(bluetoothDevice)
                mainActivity.supportFragmentManager.popBackStack()

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

    private fun dragDropAble(
        recyclerView: RecyclerView,
        rcvListDevicesAdapter: RcvListDevicesAdapter,
        list: MutableList<BluetoothDevice>
    ) {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val itemDragged: Int = viewHolder.adapterPosition
                val itemTarget: Int = target.adapterPosition

                Collections.swap(list, itemDragged, itemTarget)
                rcvListDevicesAdapter.notifyItemMoved(itemDragged, itemTarget)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}