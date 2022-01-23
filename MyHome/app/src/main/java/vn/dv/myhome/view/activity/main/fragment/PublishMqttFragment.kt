package vn.dv.myhome.view.activity.main.fragment

import android.view.View
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.BaseFragment
import vn.dv.myhome.R

class PublishMqttFragment : BaseFragment(R.layout.fragment_publish_mqtt) {

    @BindView(R.id.edt_topic_publish)
    lateinit var edtTopicPublish: TextInputEditText

    @BindView(R.id.edt_content_publish)
    lateinit var edtContentPublish: TextInputEditText

    @BindView(R.id.btn_publish)
    lateinit var btnPublish: View

    override fun initVar(view: View) {
    }

    override fun initListener() {
        btnPublish.setOnClickListener {
            (activity as BaseActivity).publishTopic(
                edtTopicPublish.text.toString(),
                edtContentPublish.text.toString()
            )
        }
    }

    override fun requestData() {
    }

}