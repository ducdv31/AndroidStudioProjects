package com.example.myhome.ui.view.fragment.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myhome.R
import com.example.myhome.data.model.weatherapi.ForecastResponse
import com.example.myhome.databinding.FragmentHomeDataBinding
import com.example.myhome.ui.view.activity.history.HistoryDataActivity
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel
import com.example.myhome.ui.viewmodel.weatherapi.WeatherApiVewModel
import com.example.myhome.utils.Constants
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class HomeDataFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val dhtViewmodel: DhtViewmodel by activityViewModels()

    private val weatherApiViewModel: WeatherApiVewModel by activityViewModels()
    private val TAG = HomeDataFragment::class.java.simpleName
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentHomeDataBinding
    private lateinit var tvTempHumi: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var ratingBar: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_data, container, false)
        val fragView = binding.root
        tvTempHumi = fragView.findViewById(R.id.tv_temp_humi)
        swipeRefreshLayout = fragView.findViewById(R.id.refresh_container)
        ratingBar = fragView.findViewById(R.id.rating)
        mainActivity = activity as MainActivity

        ratingBar.rating = 2F
        CoroutineScope(Dispatchers.Main).launch {
            repeat(1000) {
                delay(1000)
                ratingBar.rating = Random.nextDouble(0.0,5.0).toFloat()
            }
        }
        /* binding data */
        binding.lifecycleOwner = mainActivity
        binding.dhtVM = dhtViewmodel
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

        loadData()
    }

    private fun loadData() {
        swipeRefreshLayout.isRefreshing = true
        dhtViewmodel.getDataLatest(
            onSuccess = { swipeRefreshLayout.isRefreshing = false },
            onFailure = {
                swipeRefreshLayout.isRefreshing = false
                Snackbar.make(
                    swipeRefreshLayout,
                    Constants.LOAD_DATA_ERROR,
                    Snackbar.LENGTH_SHORT
                ).show()
            })
        weatherApiViewModel.getForecast(
            "Hanoi",
            3,
            onSuccess = {
                swipeRefreshLayout.isRefreshing = false
                it?.let {
                    val jsonStr = it.body?.string()
                    val json: ForecastResponse =
                        ObjectMapper().readValue(jsonStr, ForecastResponse::class.java)
                    Log.e(TAG, "loadData: ${json.location}")
                }
            },
            onFailure = { _, t ->
                swipeRefreshLayout.isRefreshing = false
                Log.e(TAG, "loadData: $t")
            }
        )
    }

    override fun onRefresh() {
        loadData()
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleActionBar(getString(R.string.app_name))
    }

}