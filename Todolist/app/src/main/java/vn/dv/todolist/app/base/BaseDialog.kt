package vn.dv.todolist.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import vn.dv.todolist.R

abstract class BaseDialog<VB : ViewBinding>(private val inflaterVb: InflateFragment<VB>) :
    DialogFragment() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialogStyle)
        initData(savedInstanceState)
    }

    abstract fun initData(data: Bundle?)

    abstract fun initView()

    abstract fun initListener()

    abstract fun initAction()

    abstract fun initObservers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflaterVb.invoke(inflater, container, false)
        initView()
        initListener()
        initAction()
        initObservers()
        return binding.root
    }
}