package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.app.scenes.home.adapter.CategoryReminderAdapter
import vn.dv.todolist.databinding.FragmentHomeScenesBinding
import vn.dv.todolist.doimain.home.room.db.CategoryTodoDb
import vn.dv.todolist.doimain.home.room.dto.getCategoryFromEntity
import vn.dv.todolist.doimain.home.room.entity.CategoryTodoEntity
import vn.dv.todolist.infrastructure.core.recyclerview.VerticalDpDivider
import javax.inject.Inject

@AndroidEntryPoint
class HomeScenes : BaseFragment<FragmentHomeScenesBinding>(FragmentHomeScenesBinding::inflate) {

    private val ioLauncher = CoroutineScope(IO)

    @Inject
    lateinit var categoryTodoDb: CategoryTodoDb

    private val dialogInputCategory: DialogInputCategory by lazy {
        DialogInputCategory(
            getString(R.string.title_input_category),
            onClickOk = { categoryName ->
                if (categoryName?.isNotEmpty() == true) {
                    ioLauncher.launch {
                        categoryTodoDb
                            .getCategoryTodoDao()
                            .addCategory(CategoryTodoEntity(title = categoryName))
                        loadAllDataFromDb()
                    }
                }
            }
        )
    }

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
        loadAllDataFromDb()
    }

    override fun initActions() {
    }

    override fun initListener() {
        binding.btnAddList.setOnClickListener {
            if (!dialogInputCategory.isAdded) {
                dialogInputCategory.show(parentFragmentManager, null)
            }
        }
    }

    override fun initObservers() {
    }

    private fun loadAllDataFromDb() {
        ioLauncher.launch {
            val listData = categoryTodoDb.getCategoryTodoDao().getAllCategory()
            withContext(Main) {
                categoryReminderAdapter.setData(getCategoryFromEntity(listData).toMutableList())
            }
        }
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