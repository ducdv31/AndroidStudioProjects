package vn.dv.todolist.app.scenes.detailltodo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.app.scenes.detailltodo.adapter.TodoAdapter
import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.databinding.FragmentDetailTodoScreenBinding
import vn.dv.todolist.infrastructure.core.recyclerview.VerticalDpDivider
import kotlin.random.Random


@AndroidEntryPoint
class DetailTodoScreen :
    BaseFragment<FragmentDetailTodoScreenBinding>(
        FragmentDetailTodoScreenBinding::inflate
    ) {

    private val args: DetailTodoScreenArgs by navArgs()

    private val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(
            onCheckItem = { isChecked, todoModel ->
            }
        )
    }

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(
            args.titleCategory,
            R.drawable.ic_baseline_arrow_back_ios_24
        )
        initRecyclerviewTodo()
        initRecyclerviewDone()
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    private fun initRecyclerviewTodo() {
        binding.rvTodoReminder.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
        val listTodo = mutableListOf<TodoModel>()
        (0..10).forEach {
            listTodo.add(TodoModel(it, false, "Todo ${Random.nextInt()}"))
        }
        todoAdapter.setData(listTodo)
    }

    private fun initRecyclerviewDone() {
        binding.rvDoneReminder.apply {
//            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
    }

    companion object {
        const val SPACE_ITEM_TODO = 8f
    }
}