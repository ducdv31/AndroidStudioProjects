package com.example.myhome.ui.view.fragment.main

import android.content.Intent
import android.net.DnsResolver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myhome.R
import com.example.myhome.data.api.ApiClient
import com.example.myhome.data.api.ApiServices
import com.example.myhome.data.model.dht.CurrentData
import com.example.myhome.databinding.FragmentHomeDataBinding
import com.example.myhome.ui.view.activity.history.HistoryDataActivity
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.viewmodel.dht.DhtFactoryViewModel
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel
import com.example.myhome.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDataFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: DhtViewmodel by lazy {
        ViewModelProvider(
            this, DhtFactoryViewModel(requireActivity())
        )[DhtViewmodel::class.java]
    }

    private val TAG = HomeDataFragment::class.java.simpleName
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentHomeDataBinding
    private lateinit var tv_temp_humi: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_data, container, false)
        val fragView = binding.root
        tv_temp_humi = fragView.findViewById(R.id.tv_temp_humi)
        swipeRefreshLayout = fragView.findViewById(R.id.refresh_container)
        mainActivity = activity as MainActivity

        /* binding data */
        binding.lifecycleOwner = mainActivity
        binding.dhtVM = viewModel
        /* ************ */
        swipeRefreshLayout.setOnRefreshListener(this)

        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_temp_humi.setOnClickListener {
            val intent = Intent(mainActivity, HistoryDataActivity::class.java)
            intent.putExtra(Constants.NAME_SENSOR, Constants.DHT11_CHILD)
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        viewModel.getCurrentData()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleActionBar(getString(R.string.app_name))
        viewModel.getCurrentData()
    }

}