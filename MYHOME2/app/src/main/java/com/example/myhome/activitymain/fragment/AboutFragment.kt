package com.example.myhome.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity

class AboutFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_about, container, false)
        mainActivity = activity as MainActivity
        mainActivity.setActionBar(mainActivity.getString(R.string.about), true)
        return fragView
    }
}