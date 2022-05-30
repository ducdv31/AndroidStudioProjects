package vn.dv.fpttoweropeningdemo.base

import android.os.Bundle

interface IBaseFragment {
    fun initData(data: Bundle?)

    fun initViews()

    fun initActions()

    fun initListener()

    fun initObservers()

    fun onBackPressed()

    fun showLoading()

    fun hideLoading()
}