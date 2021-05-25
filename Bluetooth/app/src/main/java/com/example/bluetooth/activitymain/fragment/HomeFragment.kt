package com.example.bluetooth.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity

class HomeFragment : Fragment() {

    lateinit var devices: Button
    lateinit var send: Button
    lateinit var contextSend: EditText
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_home, container, false)

        mainActivity = activity as MainActivity
        devices =fragView.findViewById(R.id.select_device)
        send =fragView.findViewById(R.id.bt_send_bt)
        contextSend =fragView.findViewById(R.id.context_send)

        devices.setOnClickListener { mainActivity.gotoListDevices() }
        send.setOnClickListener {
            val data = contextSend.text.toString()
            mainActivity.sendData(data)
        }

        return fragView
    }

}