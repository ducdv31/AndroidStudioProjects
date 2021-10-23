package com.example.myhome.data.repository

import com.example.myhome.data.api.firebase.ApiHelperImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class FirebaseRepository
@Inject constructor(private val apiHelper: ApiHelperImpl) {

    fun getCurrentData(nameSensor: String) = apiHelper.getCurrentData(nameSensor)
}