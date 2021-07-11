package com.example.bluetooth.activityPsControl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.bluetooth.R
import com.example.bluetooth.activityPsControl.PsControlActivity
import com.example.bluetooth.initbluetooth.InitBluetooth

class ControlFragment : Fragment() {

    lateinit var ibUp:ImageButton
    lateinit var ibDown:ImageButton
    lateinit var ibRight:ImageButton
    lateinit var ibLeft:ImageButton

    lateinit var psControlActivity: PsControlActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_control, container, false)
        psControlActivity = activity as PsControlActivity
        ibUp = fragView.findViewById(R.id.control_up)
        ibDown = fragView.findViewById(R.id.control_down)
        ibLeft = fragView.findViewById(R.id.control_left)
        ibRight = fragView.findViewById(R.id.control_right)

        ibUp.setOnClickListener {
            psControlActivity.sendData("Up")
        }

        ibDown.setOnClickListener {
            psControlActivity.sendData("Down")
        }

        ibLeft.setOnClickListener {
            psControlActivity.sendData("Left")
        }

        ibRight.setOnClickListener {
            psControlActivity.sendData("Right")
        }

        return fragView
    }

}