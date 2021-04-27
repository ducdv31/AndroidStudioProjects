package com.example.myroom.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity

class HomeFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)
        val bt_mode_list_user: CardView =
            homeView.findViewById<CardView>(R.id.cardView_list_user_mode)
        val bt_mode_day_select: CardView =
            homeView.findViewById<CardView>(R.id.cardView_day_select_mode)
        val bt_mode_summary: CardView =
            homeView.findViewById<CardView>(R.id.cardView_summary_mode)

        mainActivity = activity as MainActivity
        bt_mode_list_user.setOnClickListener(View.OnClickListener {
            /* open list user activity */
            mainActivity.openListMemActivity()
        })
        bt_mode_day_select.setOnClickListener(View.OnClickListener {
            /* open list user activity */
            mainActivity.openListDayActivity()
        })
        bt_mode_summary.setOnClickListener(View.OnClickListener {
            mainActivity.openSummaryActivity()
        })

        return homeView
    }

}