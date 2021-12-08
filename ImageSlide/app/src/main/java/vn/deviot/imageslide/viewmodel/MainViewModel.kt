package vn.deviot.imageslide.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.deviot.imageslide.model.MemeItem
import vn.deviot.imageslide.repository.Repository_Impl
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository_Impl
) : ViewModel() {

    init {
        getData()
    }

    var listData: MutableLiveData<List<MemeItem>?> = MutableLiveData()

    fun getData() {
        viewModelScope.launch {
            val data = repository.getListImage()

            listData.value = data?.data?.memes
        }
    }
}