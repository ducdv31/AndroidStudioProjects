package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.app.scenes.home.adapter.CategoryReminderAdapter
import vn.dv.todolist.app.scenes.home.model.CategoryReminderModel
import vn.dv.todolist.databinding.FragmentHomeScenesBinding
import vn.dv.todolist.infrastructure.core.recyclerview.VerticalDpDivider

@AndroidEntryPoint
class HomeScenes : BaseFragment<FragmentHomeScenesBinding>(FragmentHomeScenesBinding::inflate) {

    private val categoryReminderAdapter: CategoryReminderAdapter by lazy {
        CategoryReminderAdapter(
            onClickItem = {
                val action = HomeScenesDirections.actionHomeScenesToDetailTodoScreen(
                    idCategory = it.id,
                    titleCategory = it.title
                )
                findNavController().navigate(action)
            }
        )
    }

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(getString(R.string.app_name))
        initRecyclerview()
    }

    override fun initActions() {
        val listData = listOf(
            CategoryReminderModel(1, "Ok"),
            CategoryReminderModel(2, "Hehe"),
            CategoryReminderModel(3, "Leo leo"),
        )
        categoryReminderAdapter.setData(listData.toMutableList())
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    private fun initRecyclerview() {
        binding.rvCategoryReminder.apply {
            adapter = categoryReminderAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_CATEGORY, resources))
        }
    }

    companion object {
        const val SPACE_ITEM_CATEGORY = 8f
    }
}