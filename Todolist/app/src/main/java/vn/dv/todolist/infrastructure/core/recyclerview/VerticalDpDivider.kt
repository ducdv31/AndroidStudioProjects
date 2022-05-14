package vn.dv.todolist.infrastructure.core.recyclerview

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import vn.dv.todolist.infrastructure.core.Utils

class VerticalDpDivider(
    private val dpValue: Float,
    private val resources: Resources
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.adapter?.itemCount != parent.getChildAdapterPosition(view) + 1) {
            outRect.bottom = Utils.convertDpToPixel(dpValue, resources)
        }
    }
}