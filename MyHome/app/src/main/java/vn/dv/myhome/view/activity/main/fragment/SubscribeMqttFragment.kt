package vn.dv.myhome.view.activity.main.fragment

import android.view.View
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.BaseFragment
import vn.dv.myhome.R
import vn.dv.myhome.data.local.datastore.DataStoreManager

class SubscribeMqttFragment : BaseFragment(R.layout.fragment_subscribe_mqtt) {

    @BindView(R.id.edt_topic_subscribe)
    lateinit var edtTopic: TextInputEditText

    @BindView(R.id.btn_subscribe)
    lateinit var btnSubscribe: View

    private val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(requireContext())
    }

    override fun initVar(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            edtTopic.setText(dataStoreManager.topicSubFlow.first())
        }
    }

    override fun initListener() {
        btnSubscribe.setOnClickListener {
            (activity as BaseActivity).subscribeTopic(edtTopic.text.toString())
            setTopicToLocal(edtTopic.text.toString())
        }
    }

    override fun requestData() {
    }

    private fun setTopicToLocal(topic: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.setTopicSubData(topic)
        }
    }

}