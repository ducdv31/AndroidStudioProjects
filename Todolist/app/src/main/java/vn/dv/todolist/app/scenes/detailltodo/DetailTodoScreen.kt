package vn.dv.todolist.app.scenes.detailltodo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.databinding.FragmentDetailTodoScreenBinding


@AndroidEntryPoint
class DetailTodoScreen :
    BaseFragment<FragmentDetailTodoScreenBinding>(
        FragmentDetailTodoScreenBinding::inflate
    ) {

    private val args: DetailTodoScreenArgs by navArgs()

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(
            args.titleCategory,
            R.drawable.ic_baseline_arrow_back_ios_24
        )
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    override fun onResume() {
        super.onResume()
    }
}