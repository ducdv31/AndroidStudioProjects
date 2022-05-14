package vn.dv.todolist.app.scenes.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.dv.todolist.app.ext.toInvisible
import vn.dv.todolist.app.ext.toVisible
import vn.dv.todolist.app.scenes.home.model.CategoryReminderModel
import vn.dv.todolist.databinding.ItemCategoryReminderBinding

class CategoryReminderAdapter(
    private val onClickItem: (CategoryReminderModel) -> Unit
) : RecyclerView.Adapter<CategoryReminderAdapter.CategoryVH>() {

    private var listItemCategory: MutableList<CategoryReminderModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    infix fun setData(list: MutableList<CategoryReminderModel>) {
        this.listItemCategory = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val view = ItemCategoryReminderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryVH(view)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        val itemCategory = listItemCategory[position]
        holder.setDetailData(
            itemCategory,
            false,
            onClickItem
        )
    }

    override fun getItemCount(): Int = listItemCategory.size

    inner class CategoryVH(
        private val itemViewBinding: ItemCategoryReminderBinding
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun setDetailData(
            data: CategoryReminderModel,
            isShowLineBottom: Boolean,
            onClickItem: (CategoryReminderModel) -> Unit
        ) {
            with(itemViewBinding) {
                itemCategory.setOnClickListener { onClickItem.invoke(data) }
                titleCategory.text = data.title
                if (isShowLineBottom) lineBottom.toVisible() else lineBottom.toInvisible()
            }
        }
    }
}