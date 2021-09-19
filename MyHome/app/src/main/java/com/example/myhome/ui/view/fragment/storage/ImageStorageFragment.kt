package com.example.myhome.ui.view.fragment.storage

import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.image.ImageStorageAdapter
import com.example.myhome.ui.viewmodel.storage.ImageStorageFactoryViewModel
import com.example.myhome.ui.viewmodel.storage.ImageStorageViewModel
import com.example.myhome.utils.Constants
import com.example.myhome.utils.showimage.PlayImageBookAnimActivity
import java.util.*

class ImageStorageFragment : BaseFragment() {

    private val TAG = ImageStorageFragment::class.java.simpleName
    private val imageStorageViewModel: ImageStorageViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ImageStorageFactoryViewModel(requireActivity())
        )[ImageStorageViewModel::class.java]
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageStorageAdapter: ImageStorageAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun getLayout(): Int {
        return R.layout.fragment_image_storage
    }

    override fun initVar(rootView: View) {
        progressBar = rootView.findViewById(R.id.progress_bar)
        recyclerView = rootView.findViewById(R.id.rv_storage)
        swipeRefreshLayout = rootView.findViewById(R.id.refresh_container)
        imageStorageAdapter = ImageStorageAdapter(requireContext()) { listRes, position ->
            val intent = Intent(requireContext(), PlayImageBookAnimActivity::class.java)
            intent.putStringArrayListExtra(
                Constants.LIST_IMAGE_RES_KEY,
                listRes as ArrayList<String>
            )
            intent.putExtra(Constants.POSITION_IMAGE_KEY, position)
            startActivity(intent)
        }
        gridLayoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = imageStorageAdapter

        swipeRefreshLayout.setOnRefreshListener {
            imageStorageViewModel.getListImage(
                imageStorageAdapter,
                {
                    swipeRefreshLayout.isRefreshing = false
                }, {
                    swipeRefreshLayout.isRefreshing = false
                })
        }
    }

    override fun initListener() {
        showLoading(true)

        imageStorageViewModel.getListImage(
            imageStorageAdapter,
            {
                showLoading(false)
            }, {
                showLoading(false)
            })
    }

    override fun handleLogic() {

    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }


}