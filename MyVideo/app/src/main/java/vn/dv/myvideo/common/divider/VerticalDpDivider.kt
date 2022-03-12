package vn.dv.myvideo.common.divider

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import vn.dv.myvideo.utils.Utils

class VerticalDpDivider(
    private val resource: Resources,
    private val verticalSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildLayoutPosition(view)
        val size = parent.adapter?.itemCount ?: 0
        if (pos < size - 1) {
            outRect.bottom = Utils.convertDpToPixel(verticalSpace, resource)
        }
    }
}