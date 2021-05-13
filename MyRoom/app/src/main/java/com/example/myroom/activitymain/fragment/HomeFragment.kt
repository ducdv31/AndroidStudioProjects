package com.example.myroom.activitymain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitymain.MyApplication

class HomeFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    companion object {
        var bt_mode_summary: CardView? = null
        var bt_mode_day_select: CardView? = null
        var bt_mode_list_user: CardView? = null
        var bt_mode_permission_user: CardView? = null
//        var bt_mode_task_user: CardView? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)
        bt_mode_list_user =
            homeView.findViewById<CardView>(R.id.cardView_list_user_mode)
        bt_mode_day_select =
            homeView.findViewById<CardView>(R.id.cardView_day_select_mode)
        bt_mode_summary =
            homeView.findViewById<CardView>(R.id.cardView_summary_mode)
        bt_mode_permission_user =
            homeView.findViewById(R.id.cardView_user_permission_mode)

        mainActivity = activity as MainActivity
        bt_mode_list_user?.setOnClickListener(View.OnClickListener {
            /* open list user activity */
            mainActivity.openListMemActivity()
        })
        bt_mode_day_select?.setOnClickListener(View.OnClickListener {
            /* open list user activity */
            mainActivity.openListDayActivity()
        })
        bt_mode_summary?.setOnClickListener(View.OnClickListener {
            mainActivity.openSummaryActivity()
        })

        bt_mode_permission_user?.setOnClickListener {
            mainActivity.openUserPermissionActivity()
        }

        return homeView
    }

    override fun onResume() {
        super.onResume()
        MyApplication.getUIDPermission(mainActivity)
    }

}