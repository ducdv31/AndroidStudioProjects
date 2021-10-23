package com.example.myhome.di

import com.example.myhome.data.api.ApiHelper
import com.example.myhome.data.api.ApiHelperImpl
import com.example.myhome.data.api.ApiServices
import com.example.myhome.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeActivityModule {

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setDateFormat(Constants.DATE_FORMAT_GSON).create()


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) :Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl
}