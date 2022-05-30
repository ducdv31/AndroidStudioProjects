package vn.dv.fpttoweropeningdemo.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import vn.dv.fpttoweropeningdemo.R
import vn.dv.fpttoweropeningdemo.databinding.LayoutBannerHomeBinding
import vn.dv.fpttoweropeningdemo.scene.home.adapter.NewsAdapter
import vn.dv.fpttoweropeningdemo.scene.home.model.NewsModel

class BannerHome @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding = LayoutBannerHomeBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(context) }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BannerHome,
            0,
            0
        ).run {
            try {
                binding.viewPagerNews.apply {
                    adapter = newsAdapter
                }
                binding.dotsIndicator.apply {
                    dotsColor = getColor(
                        R.styleable.BannerHome_bh_dot_color,
                        ContextCompat.getColor(context, R.color.gray_400)
                    )
                    selectedDotColor = getColor(
                        R.styleable.BannerHome_bh_selected_dot_color,
                        ContextCompat.getColor(context, R.color.purple_700)
                    )
                    attachTo(binding.viewPagerNews)
                }
            } finally {
                recycle()
            }
        }
    }

    fun setBannerData(list: MutableList<NewsModel>) {
        newsAdapter.setData(list)
    }
}