package com.example.myhome.utils.showimage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.image.ImageSliderAdapter
import com.example.myhome.utils.Constants
import com.example.myhome.utils.animationViewPager2.ZoomOutPageTransformer
import de.hdodenhof.circleimageview.CircleImageView
import me.relex.circleindicator.CircleIndicator3

class PlayImageSliderActivity : AppCompatActivity() {

    private lateinit var mImageSliderVp: ViewPager2
    private lateinit var mIndicator: CircleIndicator3
    private lateinit var mImageSliderAdapter: ImageSliderAdapter
    private lateinit var close: CircleImageView
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_imagr_slider)

        initVar()
        initListener()
    }

    private fun initVar() {
        mImageSliderVp = findViewById(R.id.vpg_slider_image_storage)
        mIndicator = findViewById(R.id.circle_indicator)
        close = findViewById(R.id.close)
        mImageSliderAdapter = ImageSliderAdapter(this, getResIntent(intent))

        mImageSliderVp.adapter = mImageSliderAdapter
        mImageSliderVp.setPageTransformer(ZoomOutPageTransformer())
        mIndicator.setViewPager(mImageSliderVp)
        position = intent.getIntExtra(Constants.POSITION_IMAGE_KEY, 0)
        mImageSliderVp.currentItem = position
    }

    private fun initListener() {
        close.setOnClickListener {
            finish()
        }
    }

    private fun getResIntent(intent: Intent): ArrayList<String> {
        return intent.getStringArrayListExtra(Constants.LIST_IMAGE_RES_KEY) ?: arrayListOf()
    }
}