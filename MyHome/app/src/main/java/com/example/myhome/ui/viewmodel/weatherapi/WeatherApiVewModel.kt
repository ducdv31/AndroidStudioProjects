package com.example.myhome.ui.viewmodel.weatherapi

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myhome.data.api.ApiClientWeather
import com.example.myhome.data.model.weatherapi.ForecastResponse
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException

class WeatherApiVewModel : ViewModel() {

    private val TAG = WeatherApiVewModel::class.java.simpleName

    fun getData() {
        ApiClientWeather.getClient().getForecastNow("Hanoi", 3)?.enqueue(object :
            Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: retrofit2.Response<ForecastResponse>
            ) {
                Log.e(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
            }

        })
    }

    fun getForecast(
        locationName: String,
        days: Int,
        onSuccess: ((Response?) -> Unit) = {},
        onFailure: ((okhttp3.Call, IOException) -> Unit) = { _: okhttp3.Call,
                                                             t: IOException ->
            Log.e(TAG, "getForecast: $t")
        }

    ) {
        OkHttpClient().newCall(ApiClientWeather.request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                onFailure.invoke(call, e)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                onSuccess.invoke(response)
            }

        })
    }
}