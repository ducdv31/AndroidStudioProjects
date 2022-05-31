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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import vn.dv.todolist.app.const.Const
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

    protected fun showSnackBarWithAction(
        message: String?,
        actionText: String?,
        onClickAction: (View) -> Unit,
        onDismiss: suspend ((transientBottomBar: Snackbar?, event: Int) -> Unit) = { _, _ -> },
        onShown: suspend ((transientBottomBar: Snackbar?) -> Unit) = {}
    ) {
        Snackbar.make(binding.root, message ?: Const.EMPTY, Snackbar.LENGTH_LONG)
            .setAction(actionText) {
                onClickAction.invoke(it)
            }
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    CoroutineScope(IO).launch {
                        onDismiss.invoke(transientBottomBar, event)
                    }
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                    CoroutineScope(IO).launch {
                        onShown.invoke(transientBottomBar)
                    }
                }
            })
            .show()
    }

    protected fun showSnackBar(
        message: String?,
        onDismiss: suspend ((transientBottomBar: Snackbar?, event: Int) -> Unit) = { _, _ -> },
        onShown: suspend ((transientBottomBar: Snackbar?) -> Unit) = {}
    ) {
        Snackbar.make(binding.root, message ?: Const.EMPTY, Snackbar.LENGTH_LONG)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    CoroutineScope(IO).launch {
                        onDismiss.invoke(transientBottomBar, event)
                    }
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                    CoroutineScope(IO).launch {
                        onShown.invoke(transientBottomBar)
                    }
                }
            })
            .show()
    }
}