package com.example.myhome.ui.viewmodel.dht

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.R
import com.example.myhome.data.api.ApiClient
import com.example.myhome.data.api.ApiServices
import com.example.myhome.data.model.dht.CurrentData
import com.example.myhome.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DhtViewmodel(
    context: Context
) : ViewModel() {

    private val apiServices: ApiServices by lazy { ApiClient.getClient() }
    val thVal: MutableLiveData<String> = MutableLiveData(context.getString(R.string.temp_humi))

    init {
        getCurrentData()
    }

    fun getCurrentData(
        onLoadSuccess: () -> Unit = {},
        onLoadFailure: () -> Unit = {}
    ) {
        apiServices.getCurrentData(Constants.DHT11_CHILD)
            ?.enqueue(object : Callback<CurrentData> {
                override fun onResponse(
                    call: Call<CurrentData>,
                    response: Response<CurrentData>
                ) {
                    thVal.value = setTHFormatValue(response.body()?.t, response.body()?.h)
                    onLoadSuccess.invoke()
                }

                override fun onFailure(call: Call<CurrentData>, t: Throwable) {
                    onLoadFailure.invoke()
                }

            })
    }

    fun setTHFormatValue(t: Int? = 0, h: Int? = 0): String {
        return "$t ºC | $h %"
    }

}