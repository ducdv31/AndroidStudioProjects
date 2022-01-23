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

class PublishMqttFragment : BaseFragment(R.layout.fragment_publish_mqtt) {

    @BindView(R.id.edt_topic_publish)
    lateinit var edtTopicPublish: TextInputEditText

    @BindView(R.id.edt_content_publish)
    lateinit var edtContentPublish: TextInputEditText

    @BindView(R.id.btn_publish)
    lateinit var btnPublish: View

    private val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(requireContext())
    }

    override fun initVar(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            edtTopicPublish.setText(dataStoreManager.topicPubFlow.first())
            edtContentPublish.setText(dataStoreManager.messagePubFlow.first())
        }
    }

    override fun initListener() {
        btnPublish.setOnClickListener {
            (activity as BaseActivity).publishTopic(
                edtTopicPublish.text.toString(),
                edtContentPublish.text.toString()
            )
            setTopicAndMessageToLocal(
                edtTopicPublish.text.toString(),
                edtContentPublish.text.toString()
            )
        }
    }

    override fun requestData() {
    }

    private fun setTopicAndMessageToLocal(topic: String, message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.setTopicPubData(topic)
            dataStoreManager.setMessagePubData(message)
        }
    }

}