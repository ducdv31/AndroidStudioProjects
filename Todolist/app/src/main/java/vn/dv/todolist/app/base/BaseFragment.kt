package vn.dv.todolist.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import vn.dv.todolist.databinding.FragmentBaseBinding
import vn.dv.todolist.databinding.ToolBarLayoutBinding

typealias InflateFragment<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<VB : ViewBinding>(
    private val inflateFragment: InflateFragment<VB>
) : Fragment(), IBaseFragment, ToolBarController {

    private lateinit var fragmentBase: FragmentBaseBinding
    lateinit var binding: VB
        private set

    override val toolBar: ToolBarLayoutBinding
        get() = fragmentBase.toolBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBase = FragmentBaseBinding.inflate(inflater, container, false)
        binding = inflateFragment.invoke(inflater, fragmentBase.frameContainer, true)
        initViews()
        initActions()
        initListener()
        initObservers()
        return fragmentBase.root
    }

    fun showToast(message: String?) {
        (activity as? BaseActivity<*>)?.showToast(message)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? OnBackPressedDispatcherOwner)
            ?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner) {
                onBackPressed()
            }
    }

    override fun onBackPressed() {
        if (!findNavController().popBackStack()) {
            activity?.finish()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClickStartIcon() {
        onBackPressed()
    }

    override fun onClickEndIcon() {
    }
}