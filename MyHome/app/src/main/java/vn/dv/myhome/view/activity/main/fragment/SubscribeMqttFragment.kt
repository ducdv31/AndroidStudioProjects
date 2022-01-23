package vn.dv.myhome.view.activity.main.fragment

import android.view.View
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.BaseFragment
import vn.dv.myhome.R

class SubscribeMqttFragment : BaseFragment(R.layout.fragment_subscribe_mqtt) {

    @BindView(R.id.edt_topic_subscribe)
    lateinit var edtTopic: TextInputEditText

    @BindView(R.id.btn_subscribe)
    lateinit var btnSubscribe: View

    override fun initVar(view: View) {
    }

    override fun initListener() {
        btnSubscribe.setOnClickListener {
            (activity as BaseActivity).subscribeTopic(edtTopic.text.toString())
        }
    }

    override fun requestData() {
    }

}