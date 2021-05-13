package com.example.myroom.activitytask.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myroom.R

class UserTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userTaskView = inflater.inflate(R.layout.fragment_user_task, container, false)

        return userTaskView
    }

}