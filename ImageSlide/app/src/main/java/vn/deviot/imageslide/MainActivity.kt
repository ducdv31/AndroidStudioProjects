package vn.deviot.imageslide

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import dagger.hilt.android.AndroidEntryPoint
import vn.deviot.imageslide.adapter.SlideAdapter
import vn.deviot.imageslide.adapter.ViewPagerAdapter
import vn.deviot.imageslide.anim_viewpager.PagerTransformation
import vn.deviot.imageslide.model.MemeItem
import vn.deviot.imageslide.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @BindView(R.id.viewpager)
    lateinit var viewpager: ViewPager2

    @Inject
    lateinit var slideAdapter: SlideAdapter

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(80))
            addTransformer(PagerTransformation())
        }

        viewpager.apply {
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
            setPageTransformer(compositePageTransformer)
            adapter = viewPagerAdapter
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        mainViewModel.listData.observe(this) {
            it?.let { list ->
                viewPagerAdapter.setData(list as MutableList<MemeItem>)
            }
        }
    }
}