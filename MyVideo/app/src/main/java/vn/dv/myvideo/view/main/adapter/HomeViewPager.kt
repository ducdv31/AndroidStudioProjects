package vn.dv.myvideo.view.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.dv.myvideo.view.main.enum.EnumHomeViewpager
import vn.dv.myvideo.view.main.fragment.PopularImageFragment
import vn.dv.myvideo.view.main.fragment.PopularVideoFragment

class HomeViewPager(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = EnumHomeViewpager.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            EnumHomeViewpager.PopularImage.ordinal -> PopularImageFragment()
            EnumHomeViewpager.PopularVideo.ordinal -> PopularVideoFragment()
            else -> PopularImageFragment()
        }
    }
}