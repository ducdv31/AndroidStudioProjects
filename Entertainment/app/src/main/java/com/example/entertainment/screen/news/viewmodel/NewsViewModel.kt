package com.example.entertainment.screen.news.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entertainment.data.EMPTY
import com.example.entertainment.data.FINANCE
import com.example.entertainment.data.news.model.Article
import com.example.entertainment.data.news.model.ResponseNews
import com.example.entertainment.data.repository.Repository_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    private val TAG = NewsViewModel::class.java.simpleName

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoadMore: MutableState<Boolean> = mutableStateOf(false)
    val responseNews: MutableState<ResponseNews?> = mutableStateOf(null)
    var articles: SnapshotStateList<Article> = mutableStateListOf()
    var input: String = EMPTY
    var page = 1

    init {
        getFullDataNewsSearch(FINANCE)
    }

    fun getFullDataNewsSearch(
        inputSearch: String
    ) {
        input = inputSearch
        isLoading.value = true
        page = 1
        viewModelScope.launch {
            try {
                val response = repositoryImpl.getFullDataNewsSearch(
                    page = page,
                    inputSearch = inputSearch
                )
                responseNews.value = response
                articles.clear()
                articles.addAll(response?.articles ?: listOf())
            } catch (e: Exception) {
                Log.e(TAG, "getFullDataNewsSearch: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadMoreDataSNewsSearch() {
        page++
        isLoadMore.value = true
        viewModelScope.launch {
            try {
                val response = repositoryImpl.getFullDataNewsSearch(
                    page = page,
                    inputSearch = input
                )
                responseNews.value = response
                articles.addAll(response?.articles ?: listOf())
            } catch (e: Exception) {
                Log.e(TAG, "getFullDataNewsSearch: ${e.message}")
            } finally {
                isLoadMore.value = false
            }
        }
    }
}