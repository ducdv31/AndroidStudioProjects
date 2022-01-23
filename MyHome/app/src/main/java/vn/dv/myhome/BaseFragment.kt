package vn.dv.myhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment(layoutRes) {

    private lateinit var unBinder: Unbinder

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
    }

    abstract fun initVar(view: View)

    abstract fun initListener()

    abstract fun requestData()

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}