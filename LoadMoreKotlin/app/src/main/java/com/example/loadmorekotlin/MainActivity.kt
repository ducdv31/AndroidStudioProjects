package com.example.loadmorekotlin

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @BindView(R.id.rv)
    lateinit var rv: RecyclerView

    private lateinit var llmn: LinearLayoutManager

    @Inject
    lateinit var userAdapter: UserAdapter

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false

    private var listUser: MutableList<String> = mutableListOf()
    private var allNumPage = 5
    private var current = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        llmn = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.apply {
            layoutManager = llmn
            adapter = userAdapter
        }

        rv.addOnScrollListener(object : PagingNationScrollListener(llmn) {
            override fun loadMoreItem() {
                isLoading = true
                current += 1
                loadMore()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })

        getFirstData()
    }

    private fun getFirstData() {
        listUser = getListUser()
        userAdapter.setData(listUser)

        if (current < allNumPage) {
            userAdapter.addFooterLoading()
        } else {
            isLastPage = true
        }
    }

    private fun getListUser(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        (0..10).forEach { _ ->
            list.add("User")
        }
        return list
    }

    private fun loadMore() {
        Handler().postDelayed({
            val list = getListUser()

            userAdapter.removeLoading()
            listUser.addAll(list)
            userAdapter.notifyDataSetChanged()

            isLoading = false

            if (current < allNumPage) {
                userAdapter.addFooterLoading()
            } else {
                isLastPage = true
            }
        }, 2000)
    }
}