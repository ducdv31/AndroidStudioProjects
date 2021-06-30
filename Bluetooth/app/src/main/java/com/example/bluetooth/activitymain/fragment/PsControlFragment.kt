package com.example.bluetooth.activitymain.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity

class PsControlFragment : Fragment() {

    lateinit var mainActivity: MainActivity

    lateinit var ibUp: ImageButton
    lateinit var ibDown: ImageButton
    lateinit var ibLeft: ImageButton
    lateinit var ibRight: ImageButton
    lateinit var valueSeekBar: TextView
    lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_ps_control, container, false)
        ibUp = fragView.findViewById(R.id.control_up)
        ibDown = fragView.findViewById(R.id.control_down)
        ibLeft = fragView.findViewById(R.id.control_left)
        ibRight = fragView.findViewById(R.id.control_right)
        valueSeekBar = fragView.findViewById(R.id.seekBar_value)
        seekBar = fragView.findViewById(R.id.seekBar)

        seekBar.max = 360
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                valueSeekBar.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        ibUp.setOnClickListener {
            mainActivity.sendData("Up")
        }

        ibDown.setOnClickListener {
            mainActivity.sendData("Down")
        }

        ibLeft.setOnClickListener {
            mainActivity.sendData("Left")
        }

        ibRight.setOnClickListener {
            mainActivity.sendData("Right")
        }
        return fragView
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideSystemUI()
        mainActivity.showActionBar(false)
        mainActivity.requestOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity.showSystemUI()
        mainActivity.showActionBar(true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity.releaseInstance()
    }
}