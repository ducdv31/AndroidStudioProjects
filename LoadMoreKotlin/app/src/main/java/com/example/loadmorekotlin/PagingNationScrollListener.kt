package com.example.loadmorekotlin

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagingNationScrollListener(
    private val llmn: LinearLayoutManager
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = llmn.childCount
        val totalItemCount = llmn.itemCount
        val firstItemPos = llmn.findFirstVisibleItemPosition()

        if (isLoading() || isLastPage()) {
            return
        }

        if (firstItemPos >= 0 && visibleItemCount + firstItemPos >= totalItemCount) {
            loadMoreItem()
        }

    }

    abstract fun loadMoreItem()
    abstract fun isLoading(): Boolean
    abstract fun isLastPage(): Boolean
}