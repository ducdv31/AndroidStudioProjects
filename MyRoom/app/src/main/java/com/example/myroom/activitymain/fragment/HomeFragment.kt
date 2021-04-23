package com.example.myroom.activitymain.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.R

class HomeFragment : Fragment() {

    private var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)
        val bt_mode_list_user = homeView.findViewById<View>(R.id.cardView_list_user_mode)
        val bt_mode_day_select = homeView.findViewById<View>(R.id.cardView_day_select_mode)
        mainActivity = activity as MainActivity?
        bt_mode_list_user.setOnClickListener(View.OnClickListener { v: View? ->
            /* open list user activity */
            mainActivity?.openListMemActivity()
        })
        bt_mode_day_select.setOnClickListener(View.OnClickListener { v ->
            /* open list user activity */
            mainActivity?.openListDayActivity()
        })
        return homeView
    }

}