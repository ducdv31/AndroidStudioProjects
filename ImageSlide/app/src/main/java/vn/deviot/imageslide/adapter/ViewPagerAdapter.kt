package vn.deviot.imageslide.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.deviot.imageslide.fragment.MemeFragment
import vn.deviot.imageslide.model.MemeItem

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var listMeme: MutableList<MemeItem> = mutableListOf()

    fun setData(list: MutableList<MemeItem>) {
        this.listMeme = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listMeme.size

    override fun createFragment(position: Int): Fragment {
        val memeFragment = MemeFragment()
        val bundle = Bundle()
        bundle.apply {
            putParcelable(DATA_KEY, listMeme[position])
        }
        memeFragment.arguments = bundle
        return memeFragment
    }
}

const val DATA_KEY = "DATA_KEY"