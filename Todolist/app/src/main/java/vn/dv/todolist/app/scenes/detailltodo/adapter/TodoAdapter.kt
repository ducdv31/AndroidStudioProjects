package vn.dv.todolist.app.scenes.detailltodo.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.databinding.ItemTodoReminderBinding

class TodoAdapter(
    private val onCheckItem: (Boolean, TodoModel) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoVH>() {

    private var listTodo: MutableList<TodoModel> = mutableListOf()

    fun setData(list: MutableList<TodoModel>) {
        this.listTodo = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val binding = ItemTodoReminderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoVH(binding)
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        val itemTodo = listTodo[position]
        holder.bind(itemTodo, onCheckItem = { isChecked, todoModel ->
            onCheckItem.invoke(isChecked, todoModel)
        })
    }

    override fun getItemCount(): Int = listTodo.size

    inner class TodoVH(
        private var binding: ItemTodoReminderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val foregroundLayout: View
            get() = binding.foregroundLayout

        fun bind(todoModel: TodoModel, onCheckItem: (Boolean, TodoModel) -> Unit) {
            with(binding) {
                titleTodo.text = if (todoModel.checked) {
                    spannableText(todoModel.title)
                } else {
                    todoModel.title
                }
                cbTodo.apply {
                    isChecked = todoModel.checked
                    setOnCheckedChangeListener { compoundButton, isChecked ->
                        onCheckItem.invoke(isChecked, todoModel)
                    }
                }
            }
        }
    }

    private fun spannableText(text: String): SpannableStringBuilder {
        val spanTextBuilder = SpannableStringBuilder(text)
        spanTextBuilder.setSpan(
            UnderlineSpan(),
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spanTextBuilder
    }

    infix fun removeItemAt(index: Int) {
        listTodo.removeAt(index)
        notifyItemRemoved(index)
    }

    fun undoItem(todoModel: TodoModel, index: Int) {
        listTodo.add(index, todoModel)
        notifyItemInserted(index)
    }
}