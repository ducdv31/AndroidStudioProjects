package com.example.myhome.ui.viewmodel.dht

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.data.model.dht.CurrentData
import com.example.myhome.data.repository.FirebaseDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DhtViewmodel
@Inject constructor(
) : ViewModel() {
    @Inject
    lateinit var repository: FirebaseDataRepository

    val thVal: MutableLiveData<String> = MutableLiveData("---- | ----")

    fun getDataLatest(
        onSuccess: ((CurrentData?) -> Unit) = {},
        onFailure: ((Throwable) -> Unit) = {}
    ) {
        repository.getCurrentData(
            onLoadSuccess = {
                onSuccess.invoke(it)
                it?.let { currentData ->
                    thVal.value = setTHFormatValue(currentData.t, currentData.h)
                }
            }, onLoadFailure = {
                onFailure.invoke(it)
            }
        )
    }

    private fun setTHFormatValue(t: Int? = 0, h: Int? = 0): String {
        return "$t ºC | $h %"
    }

}