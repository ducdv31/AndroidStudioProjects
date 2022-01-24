package vn.dv.myhome

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.snackbar.Snackbar
import vn.dv.myhome.utils.bus.GlobalBus

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment(layoutRes) {

    private lateinit var unBinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalBus.getBus().register(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unBinder = ButterKnife.bind(this, view)
        initVar(view)
        initListener()
        requestData()
    }

    override fun onDetach() {
        super.onDetach()
        unBinder.unbind()
        GlobalBus.getBus().unregister(this)
    }

    abstract fun initVar(view: View)

    abstract fun initListener()

    abstract fun requestData()

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}