package vn.dv.fpttoweropeningdemo.scene.home

import android.os.Bundle
import vn.dv.fpttoweropeningdemo.base.BaseFragment
import vn.dv.fpttoweropeningdemo.databinding.FragmentHomeScreenBinding
import vn.dv.fpttoweropeningdemo.scene.home.model.NewsModel

class HomeScreen : BaseFragment<FragmentHomeScreenBinding>(FragmentHomeScreenBinding::inflate) {

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        binding.bannerHome.setBannerData(getListBanner().toMutableList())
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    private fun getListBanner(): List<NewsModel> {
        val listNews = mutableListOf<NewsModel>()
        listNews.add(NewsModel("https://picsum.photos/200/300", "Image 1"))
        listNews.add(NewsModel("https://picsum.photos/id/237/200/300", "Image 2"))
        listNews.add(NewsModel("https://picsum.photos/seed/picsum/200/300", "Image 3"))
        listNews.add(NewsModel("https://picsum.photos/200/300?grayscale", "Image 4"))
        return listNews
    }
}