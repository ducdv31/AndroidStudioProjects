package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import vn.dv.todolist.app.base.BaseDialog
import vn.dv.todolist.app.ext.setWidthPercent
import vn.dv.todolist.databinding.LayoutInputNameCategoryBinding

class DialogInputCategory :
    BaseDialog<LayoutInputNameCategoryBinding>(LayoutInputNameCategoryBinding::inflate) {

    override fun initData(data: Bundle?) {
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.edtCategory.doOnTextChanged { text, start, before, count -> }
    }

    override fun initAction() {
    }

    override fun initObservers() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(90)
    }
}