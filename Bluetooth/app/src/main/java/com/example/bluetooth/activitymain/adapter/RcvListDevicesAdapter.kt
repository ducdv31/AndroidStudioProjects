package com.example.bluetooth.activitymain.adapter

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.R

class RcvListDevicesAdapter() : RecyclerView.Adapter<RcvListDevicesAdapter.DevicesViewHolder>() {
    interface IClickDevice {
        fun onClickDevice(bluetoothDevice: BluetoothDevice)
    }

    var listDevices: MutableList<BluetoothDevice> = mutableListOf()
    lateinit var iClickDevice: IClickDevice


    constructor(iClickDevice: IClickDevice) : this() {
        this.iClickDevice = iClickDevice
    }

    fun setData(list: MutableList<BluetoothDevice>){
        listDevices = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_devices, parent, false)
        return DevicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val bluetoothDevice = listDevices[position] ?: return
        holder.name.text = bluetoothDevice.name
        holder.mac.text = bluetoothDevice.toString()
        holder.cardView.setOnClickListener {
            iClickDevice.onClickDevice(bluetoothDevice)
        }
    }

    override fun getItemCount(): Int {
        return listDevices.size
    }

    class DevicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.card_device)
        val name = itemView.findViewById<TextView>(R.id.name_device)
        val mac = itemView.findViewById<TextView>(R.id.mac_address)
    }
}