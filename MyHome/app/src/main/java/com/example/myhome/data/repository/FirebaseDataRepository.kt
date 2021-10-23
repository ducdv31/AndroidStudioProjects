package com.example.myhome.data.repository

import com.example.myhome.data.api.ApiHelperImpl
import com.example.myhome.data.model.dht.CurrentData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class FirebaseDataRepository
@Inject constructor(private val apiHelper: ApiHelperImpl) {

    fun getCurrentData(
        onLoadSuccess: (CurrentData?) -> Unit = {},
        onLoadFailure: (Throwable) -> Unit = {}
    ) {
        apiHelper.getCurrentData()
            ?.enqueue(object : Callback<CurrentData> {
                override fun onResponse(
                    call: Call<CurrentData>,
                    response: Response<CurrentData>
                ) {
                    onLoadSuccess.invoke(response.body())
                }

                override fun onFailure(call: Call<CurrentData>, t: Throwable) {
                    onLoadFailure.invoke(t)
                }

            })
    }
}