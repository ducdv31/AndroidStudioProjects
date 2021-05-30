package com.example.bluetooth.activitymain.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity

class AboutFragment : Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_about, container, false)
        mainActivity = activity as MainActivity

        mainActivity.actionBar?.title = "About"

        return fragView
    }

}