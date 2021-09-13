package com.example.myhome.ui.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myhome.R
import com.example.myhome.databinding.FragmentHomeDataBinding
import com.example.myhome.ui.view.activity.history.HistoryDataActivity
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.viewmodel.dht.DhtFactoryViewModel
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel
import com.example.myhome.utils.Constants
import com.google.android.material.snackbar.Snackbar

class HomeDataFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: DhtViewmodel by lazy {
        ViewModelProvider(
            this, DhtFactoryViewModel(requireActivity())
        )[DhtViewmodel::class.java]
    }

    private val TAG = HomeDataFragment::class.java.simpleName
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentHomeDataBinding
    private lateinit var tvTempHumi: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_data, container, false)
        val fragView = binding.root
        tvTempHumi = fragView.findViewById(R.id.tv_temp_humi)
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
        tvTempHumi.setOnClickListener {
            val intent = Intent(mainActivity, HistoryDataActivity::class.java)
            intent.putExtra(Constants.NAME_SENSOR, Constants.DHT11_CHILD)
            startActivity(intent)
        }
    }

    override fun onRefresh() {
        viewModel.getCurrentData(
            onLoadSuccess = { swipeRefreshLayout.isRefreshing = false },
            onLoadFailure = {
                swipeRefreshLayout.isRefreshing = false
                Snackbar.make(
                    swipeRefreshLayout,
                    Constants.LOAD_DATA_ERROR,
                    Snackbar.LENGTH_SHORT
                ).show()
            })
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleActionBar(getString(R.string.app_name))
        swipeRefreshLayout.isRefreshing = true
        viewModel.getCurrentData(
            onLoadSuccess = { swipeRefreshLayout.isRefreshing = false },
            onLoadFailure = {
                swipeRefreshLayout.isRefreshing = false
                Snackbar.make(
                    swipeRefreshLayout,
                    Constants.LOAD_DATA_ERROR,
                    Snackbar.LENGTH_SHORT
                ).show()
            })
    }

}