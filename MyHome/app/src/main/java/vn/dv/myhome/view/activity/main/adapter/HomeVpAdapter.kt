package vn.dv.myhome.view.activity.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class HomeVpAdapter @Inject constructor(
    @ActivityScoped fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = EBottomTabHome.values().size

    override fun createFragment(position: Int): Fragment {
        return EBottomTabHome.values().toList()[position].fragment
    }
}