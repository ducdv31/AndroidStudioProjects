package com.example.myhome.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity

class PermissionFragment : Fragment() {

    private lateinit var switchPowerOptimization: SwitchCompat
    private lateinit var back: Button
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_permission, container, false)
        switchPowerOptimization = fragView.findViewById(R.id.sw_power_optimization)
        back = fragView.findViewById(R.id.btn_back)
        mainActivity = activity as MainActivity

        back.setOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
        /* check startup */
        switchPowerOptimization.isChecked = mainActivity.isIgnorePowerOptimize()

        /* set data */
        switchPowerOptimization.setOnClickListener {

        }

        return fragView
    }
}