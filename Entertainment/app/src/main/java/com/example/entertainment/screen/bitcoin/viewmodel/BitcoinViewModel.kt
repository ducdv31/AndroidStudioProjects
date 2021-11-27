package com.example.entertainment.screen.bitcoin.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entertainment.data.bitcoin.model.Bpi
import com.example.entertainment.data.bitcoin.model.Time
import com.example.entertainment.data.repository.Repository_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BitcoinViewModel
@Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val bpi: MutableState<Bpi?> = mutableStateOf(null)
    val time: MutableState<Time?> = mutableStateOf(null)
    val disclaimer: MutableState<String?> = mutableStateOf(null)
    val chartName: MutableState<String?> = mutableStateOf(null)

    init {
        getBitcoinPrice()
    }

    fun getBitcoinPrice() {
        isLoading.value = true
        viewModelScope.launch {
            val data = repositoryImpl.getBitcoinData()
            bpi.value = data?.bpi
            time.value = data?.time
            disclaimer.value = data?.disclaimer
            chartName.value = data?.chartName
            isLoading.value = false
        }
    }
}