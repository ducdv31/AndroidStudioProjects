package com.example.myhome.utils.paging

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val visibleItemCount: Int = layoutManager.childCount
            val totalItemCount: Int = layoutManager.itemCount
            val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

            if (isLoading() or isLastPage()) {
                return
            }

            if (firstVisibleItemPosition >= maxItemPerPage()
                && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
            ) {
                loadMoreItem()
            }
        }
    }

    abstract fun maxItemPerPage(): Int
    abstract fun loadMoreItem()
    abstract fun isLoading(): Boolean
    abstract fun isLastPage(): Boolean
}