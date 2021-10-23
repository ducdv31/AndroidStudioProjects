package com.example.myhome.ui.viewmodel.dht

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.data.model.dht.CurrentData
import com.example.myhome.data.repository.FirebaseRepository
import com.example.myhome.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DhtViewmodel
@Inject constructor(
) : ViewModel() {
    @Inject
    lateinit var repository: FirebaseRepository

    val thVal: MutableLiveData<String> = MutableLiveData("---- | ----")

    fun getDataLatest(
        onSuccess: ((CurrentData?) -> Unit) = {},
        onFailure: ((Throwable) -> Unit) = {}
    ) {
        repository.getCurrentData(Constants.DHT11_CHILD)?.enqueue(object : Callback<CurrentData> {
            override fun onResponse(call: Call<CurrentData>, response: Response<CurrentData>) {
                response.body()?.let {
                    thVal.value = setTHFormatValue(it.t, it.h)
                    onSuccess.invoke(it)
                }
            }

            override fun onFailure(call: Call<CurrentData>, t: Throwable) {
                onFailure.invoke(t)
            }
        })
    }

    private fun setTHFormatValue(t: Int? = 0, h: Int? = 0): String {
        return "$t ºC | $h %"
    }

}