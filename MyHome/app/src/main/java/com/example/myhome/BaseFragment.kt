package com.example.myhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(getLayout(), container, false)

        initVar(view)
        initListener()
        handleLogic()

        return view
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun initVar(rootView: View)

    abstract fun initListener()

    abstract fun handleLogic()
}