package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.databinding.FragmentHomeScenesBinding

@AndroidEntryPoint
class HomeScenes : BaseFragment<FragmentHomeScenesBinding>(FragmentHomeScenesBinding::inflate) {

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
    }

    override fun initActions() {
    }

    override fun initListener() {
        binding.btnChangeScreen.setOnClickListener {
            val action = HomeScenesDirections.actionHomeScenesToDetailTodoScreen()
            findNavController().navigate(action)
        }
    }

    override fun initObservers() {
    }

}