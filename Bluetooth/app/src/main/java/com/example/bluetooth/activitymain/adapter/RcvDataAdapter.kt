package com.example.bluetooth.activitymain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.model.DataRS

class RcvDataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listRS: MutableList<DataRS> = mutableListOf()

    private val RECEIVED_TYPE: Int = 1
    private val SEND_TYPE: Int = 2

    fun setData(list: MutableList<DataRS>) {
        listRS = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RECEIVED_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_received, parent, false)
                return ReceivedDataViewHolder(view)
            }
            SEND_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_sent, parent, false)
                return SendDataViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_sent, parent, false)
                return SendDataViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataRS = listRS[position]
        when (holder.itemViewType) {
            SEND_TYPE -> {
                val sendDataViewHolder: SendDataViewHolder = holder as SendDataViewHolder
                sendDataViewHolder.sent.text = dataRS.context
            }
            RECEIVED_TYPE -> {
                val receivedDataViewHolder: ReceivedDataViewHolder =
                    holder as ReceivedDataViewHolder
                receivedDataViewHolder.received.text = dataRS.context
            }
        }
    }

    override fun getItemCount(): Int {
        return listRS.size
    }

    override fun getItemViewType(position: Int): Int {
        val dataRS = listRS[position]
        return if (dataRS.isSend) {
            SEND_TYPE
        } else {
            RECEIVED_TYPE
        }
    }

    class ReceivedDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val received: TextView = itemView.findViewById(R.id.tv_receiver)
    }

    class SendDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sent: TextView = itemView.findViewById(R.id.tv_sent)
    }


}