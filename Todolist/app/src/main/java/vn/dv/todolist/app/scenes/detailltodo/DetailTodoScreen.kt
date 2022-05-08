package vn.dv.todolist.app.scenes.detailltodo

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.databinding.FragmentDetailTodoScreenBinding


@AndroidEntryPoint
class DetailTodoScreen :
    BaseFragment<FragmentDetailTodoScreenBinding>(
        FragmentDetailTodoScreenBinding::inflate
    ) {
    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(
            getString(R.string.app_name),
            R.drawable.ic_baseline_arrow_back_ios_24
        )
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

}