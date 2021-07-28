package com.example.myhome.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity

class AboutFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var back: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_about, container, false)
        mainActivity = activity as MainActivity
        back = fragView.findViewById(R.id.btn_back_about)
        mainActivity.setActionBar(mainActivity.getString(R.string.about), false)
        back.setOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
        return fragView
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity.setActionBar(mainActivity.getString(R.string.my_home), false)
    }
}