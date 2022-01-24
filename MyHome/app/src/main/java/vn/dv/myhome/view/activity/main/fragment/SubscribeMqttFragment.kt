package vn.dv.myhome.view.activity.main.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.dv.myhome.BaseActivity
import vn.dv.myhome.BaseFragment
import vn.dv.myhome.R
import vn.dv.myhome.data.local.datastore.DataStoreManager
import vn.dv.myhome.utils.bus.EventBus
import vn.dv.myhome.view.activity.main.adapter.SubscribeDataAdapter
import javax.inject.Inject

@AndroidEntryPoint
class SubscribeMqttFragment : BaseFragment(R.layout.fragment_subscribe_mqtt) {

    @BindView(R.id.rv_list_data_sub)
    lateinit var rvDataSub: RecyclerView

    @BindView(R.id.edt_topic_subscribe)
    lateinit var edtTopic: TextInputEditText

    @BindView(R.id.btn_subscribe)
    lateinit var btnSubscribe: View

    private val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(requireContext())
    }

    @Inject
    lateinit var subscribeDataAdapter: SubscribeDataAdapter

    private val llmn: LinearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun initVar(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            edtTopic.setText(dataStoreManager.topicSubFlow.first())
        }

        rvDataSub.apply {
            layoutManager = llmn
            adapter = subscribeDataAdapter
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

    @Subscribe
    fun getDataReceived(subscribeDataModel: EventBus.SubscribeDataEvent) {
        subscribeDataModel.getData()?.let { subscribeDataAdapter.addDataItem(it) }
        rvDataSub.smoothScrollToPosition(subscribeDataAdapter.itemCount)
    }

}