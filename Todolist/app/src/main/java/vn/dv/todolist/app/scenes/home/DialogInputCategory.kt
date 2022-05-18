package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import vn.dv.todolist.app.base.BaseDialog
import vn.dv.todolist.app.const.Const
import vn.dv.todolist.app.ext.setWidthPercent
import vn.dv.todolist.databinding.LayoutInputNameCategoryBinding

class DialogInputCategory(
    private val title: String = Const.EMPTY,
    private val onClickCancel: (() -> Unit) = {},
    private val onClickOk: ((String?) -> Unit) = {}
) : BaseDialog<LayoutInputNameCategoryBinding>(LayoutInputNameCategoryBinding::inflate) {

    override fun initData(data: Bundle?) {
    }

    override fun initView() {
        binding.title.apply {
            text = title
            isVisible = title.isNotEmpty()
        }
    }

    override fun initListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
            onClickCancel.invoke()
        }
        binding.btnOk.setOnClickListener {
            onClickOk.invoke(binding.edtCategory.text?.toString())
            dismiss()
        }
    }

    override fun initAction() {
    }

    override fun initObservers() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(PERCENT_WIDTH_DIALOG)
    }

    companion object {
        const val PERCENT_WIDTH_DIALOG = 90
    }
}