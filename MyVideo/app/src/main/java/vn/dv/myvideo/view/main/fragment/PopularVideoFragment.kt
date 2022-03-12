package vn.dv.myvideo.view.main.fragment

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.myvideo.common.BaseFragment
import vn.dv.myvideo.databinding.FragmentPopularVideoBinding
import vn.dv.myvideo.view.main.viewmodel.VideoPexelsViewModel

@AndroidEntryPoint
class PopularVideoFragment :
    BaseFragment<FragmentPopularVideoBinding>(FragmentPopularVideoBinding::inflate) {

    private val videoPexelsViewModel: VideoPexelsViewModel by viewModels()

    override fun initData() {
    }

    override fun listener() {
        videoPexelsViewModel.videos.observe(this) {
            showToast(it?.get(1)?.url)
        }
    }

    override fun requestData() {
        videoPexelsViewModel.getPopularVideo()
    }

}