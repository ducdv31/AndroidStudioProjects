package com.example.myhome.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity
import com.example.myhome.activitymain.model.EspInfoModel
import com.example.myhome.tool.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BoardInformationFragment : Fragment() {

    private lateinit var wifiName: TextView
    private lateinit var ip: TextView
    private lateinit var gateway: TextView
    private lateinit var subnet: TextView
    private lateinit var back: Button
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_board_information, container, false)
        wifiName = fragView.findViewById(R.id.wifi_name)
        ip = fragView.findViewById(R.id.ip_address)
        gateway = fragView.findViewById(R.id.gateway)
        subnet = fragView.findViewById(R.id.subnet)
        back = fragView.findViewById(R.id.btn_back)
        mainActivity = activity as MainActivity

        mainActivity.setActionBar(getString(R.string.board_information), false)
        back.setOnClickListener {
            mainActivity.onBackPressed()
        }

        getData()


        return fragView
    }

    private fun getData() {
        FirebaseDatabase.getInstance().reference
            .child(Constant.ESP_INFOR_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val espInfoModel: EspInfoModel? = snapshot.getValue(EspInfoModel::class.java)
                    espInfoModel?.let {
                        wifiName.text = it.WiFi
                        ip.text = it.IP
                        gateway.text = it.Gateway
                        subnet.text = it.Subnet
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}