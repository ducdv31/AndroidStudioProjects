package com.example.myhome

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.my_action_bar.*

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        btn_back_action_bar.setOnClickListener {
            setOnBackActionBar()
        }

        img_user.setOnClickListener {
            setOnClickUserImg()
        }
    }

    open fun setBackRes(res: Int) {
        btn_back_action_bar.setImageResource(res)
    }

    open fun setUserImgRes(res: Int) {
        img_user.setImageResource(res)
    }

    open fun setOnBackActionBar() {
        onBackPressed()
    }

    open fun setOnClickUserImg() {

    }

    open fun setTitleActionBar(title: String) {
        title_action_bar.text = title
    }

    open fun isShowBackActionBar(isShow: Boolean) {
        if (isShow) {
            btn_back_action_bar.visibility = View.VISIBLE
        } else {
            btn_back_action_bar.visibility = View.GONE
        }
    }

    open fun isShowUserImg(isShow: Boolean) {
        if (isShow) {
            img_user.visibility = View.VISIBLE
        } else {
            img_user.visibility = View.GONE
        }
    }
}