package com.example.myhome.ui.view.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myhome.R
import com.example.myhome.databinding.FragmentHomeDataBinding
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.viewmodel.dht.DhtFactoryViewModel
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel
import kotlinx.android.synthetic.*

class HomeDataFragment : Fragment() {

    private val viewModel: DhtViewmodel by lazy {
        ViewModelProvider(
            this, DhtFactoryViewModel(requireActivity())
        )[DhtViewmodel::class.java]
    }

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentHomeDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_data, container, false)
        val fragView = binding.root
        mainActivity = activity as MainActivity

        binding.lifecycleOwner = mainActivity
        binding.dhtVM = viewModel

        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }

}