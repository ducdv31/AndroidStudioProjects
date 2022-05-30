package vn.dv.fpttoweropeningdemo.base

import androidx.annotation.DrawableRes
import vn.dv.fpttoweropeningdemo.databinding.ToolBarLayoutBinding
import vn.dv.todolist.app.ext.toInvisible
import vn.dv.todolist.app.ext.toVisible

interface ToolBarController {

    val toolBar: ToolBarLayoutBinding

    fun showToolBar(
        title: String,
        @DrawableRes startIcon: Int? = null,
        @DrawableRes endIcon: Int? = null
    ) {
        toolBar.apply {
            toolBarLayout.toVisible()
            tvTitle.text = title
        }
        showStartIcon(startIcon)
        showEndIcon(endIcon)
    }

    fun showStartIcon(@DrawableRes startIcon: Int?) {
        if (startIcon == null) {
            toolBar.imgStartIcon.toInvisible()
        } else {
            toolBar.imgStartIcon.apply {
                toVisible()
                setImageResource(startIcon)
                setOnClickListener {
                    onClickStartIcon()
                }
            }
        }
    }

    fun showEndIcon(@DrawableRes endIcon: Int?) {
        if (endIcon == null) {
            toolBar.imgEndIcon.toInvisible()
        } else {
            toolBar.imgEndIcon.apply {
                toVisible()
                setImageResource(endIcon)
                setOnClickListener {
                    onClickEndIcon()
                }
            }
        }
    }

    fun onClickStartIcon()

    fun onClickEndIcon()
}