package com.example.entertainment.screen.movie.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entertainment.data.movie.model.CategoryItem
import com.example.entertainment.data.repository.Repository_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val phimBo: MutableState<List<CategoryItem>?> = mutableStateOf(null)
    val phimLe: MutableState<List<CategoryItem>?> = mutableStateOf(null)
    val phimChieuRap: MutableState<List<CategoryItem>?> = mutableStateOf(null)
    val phimHoatHinh: MutableState<List<CategoryItem>?> = mutableStateOf(null)

    init {
        getListMovie()
    }

    private fun getListMovie() {
        isLoading.value = true
        viewModelScope.launch {
            val data = repositoryImpl.getDataMovie()

            phimBo.value = data?.phim?.phimBo
            phimLe.value = data?.phim?.phimLe
            phimChieuRap.value = data?.phim?.phimChieuRap
            phimHoatHinh.value = data?.phim?.phimHoatHinh
            isLoading.value = false
        }
    }
}