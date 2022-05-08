package vn.dv.todolist.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.r0adkll.slidr.Slidr
import vn.dv.todolist.R
import vn.dv.todolist.app.const.Const

typealias Inflater<VB> = (LayoutInflater) -> VB

abstract class BaseActivity<VB : ViewBinding>(private val inflater: Inflater<VB>) :
    AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflater.invoke(layoutInflater)
        setContentView(binding.root)
    }

    fun showToast(message: String?) {
        Toast.makeText(
            this,
            message ?: Const.EMPTY,
            Toast.LENGTH_SHORT
        ).show()
    }
}