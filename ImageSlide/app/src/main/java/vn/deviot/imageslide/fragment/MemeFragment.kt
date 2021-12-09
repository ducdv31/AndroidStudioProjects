package vn.deviot.imageslide.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.bumptech.glide.Glide
import vn.deviot.imageslide.R
import vn.deviot.imageslide.adapter.DATA_KEY
import vn.deviot.imageslide.model.MemeItem

class MemeFragment : Fragment(R.layout.fragment_meme) {

    private lateinit var unbinder: Unbinder

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.image)
    lateinit var image: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.get(DATA_KEY) as? MemeItem
        unbinder = ButterKnife.bind(this, requireView())
        data?.let {
            Glide.with(this)
                .load(data.url)
                .into(image)
            name.text = data.name
        }
    }

    override fun onDetach() {
        super.onDetach()
        unbinder.unbind()
    }
}