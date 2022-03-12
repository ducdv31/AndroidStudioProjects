package vn.dv.myvideo.view.main.fragment

import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.dv.myvideo.common.BaseFragment
import vn.dv.myvideo.databinding.FragmentMainBinding
import vn.dv.myvideo.view.main.adapter.HomeViewPager
import vn.dv.myvideo.view.main.enum.EnumHomeViewpager

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val homeViewPager: HomeViewPager by lazy {
        HomeViewPager(this)
    }

    override fun listener() {
        binding.viewPager.apply {
            adapter = homeViewPager
            offscreenPageLimit = 2
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val idByPos = EnumHomeViewpager.values().toList()[position].idMenu
                    binding.bottomNavMain.menu.findItem(idByPos).isChecked = true
                }
            })
        }
        binding.bottomNavMain.setOnItemSelectedListener { menuItem ->
            CoroutineScope(Dispatchers.IO).launch {
                EnumHomeViewpager.values().toList().forEach {
                    if (it.idMenu == menuItem.itemId) {
                        withContext(Main) {
                            binding.viewPager.currentItem =
                                EnumHomeViewpager.values().toList().indexOf(it)
                        }
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun initData() {
    }

    override fun requestData() {
    }
}