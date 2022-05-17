package vn.dv.todolist.app.scenes.detailltodo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                if (listTodo.contains(todoModel)) {
                    todoModel.checked = true
                    val pos = listTodo.indexOf(todoModel)
                    listTodo.remove(todoModel)
                    todoAdapter.notifyItemRemoved(pos)
                    listDone.add(todoModel)
                    doneAdapter.notifyItemInserted(listDone.lastIndex)
                }
            }
        )
    }

    private val doneAdapter: TodoAdapter by lazy {
        TodoAdapter(
            onCheckItem = { isChecked, todoModel ->
                if (listDone.contains(todoModel)) {
                    todoModel.checked = false
                    val pos = listDone.indexOf(todoModel)
                    listDone.remove(todoModel)
                    doneAdapter.notifyItemRemoved(pos)
                    listTodo.add(todoModel)
                    todoAdapter.notifyItemInserted(listTodo.lastIndex)
                }
            }
        )
    }

    private val listAll: MutableList<TodoModel> by lazy {
        val list = mutableListOf<TodoModel>()
        (0..10).forEach {
            list.add(TodoModel(it, Random.nextBoolean(), "Todo ${Random.nextInt()}"))
        }
        list
    }

    private val listTodo: MutableList<TodoModel> by lazy {
        val listTd = mutableListOf<TodoModel>()
        listAll.forEach {
            if (!it.checked) {
                listTd.add(it)
            }
        }
        listTd
    }

    private val listDone: MutableList<TodoModel> by lazy {
        val listD = mutableListOf<TodoModel>()
        listAll.forEach {
            if (it.checked) listD.add(it)
        }
        listD
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
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.absoluteAdapterPosition
                    listTodo.removeAt(pos)
                    todoAdapter.notifyItemRemoved(pos)
                }
            })
        binding.rvTodoReminder.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
        itemTouchHelper.attachToRecyclerView(binding.rvTodoReminder)
        todoAdapter.setData(listTodo)
    }

    private fun initRecyclerviewDone() {
        binding.rvDoneReminder.apply {
            adapter = doneAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
        doneAdapter.setData(listDone)
    }

    companion object {
        const val SPACE_ITEM_TODO = 8f
    }
}