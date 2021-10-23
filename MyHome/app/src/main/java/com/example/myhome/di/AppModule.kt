package com.example.myhome.di

import com.example.myhome.data.api.firebase.ApiHelper
import com.example.myhome.data.api.firebase.ApiHelperImpl
import com.example.myhome.data.api.firebase.ApiServices
import com.example.myhome.data.api.music.ApiMusicHelper
import com.example.myhome.data.api.music.ApiMusicHelperImpl
import com.example.myhome.data.api.music.ApiServicesMusics
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
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat(Constants.DATE_FORMAT_GSON).create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl

    @Provides
    @Singleton
    fun provideApiServiceMusic(gson: Gson): ApiServicesMusics = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_MUSIC)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiServicesMusics::class.java)

    @Provides
    @Singleton
    fun provideApiMusicHelper(apiMusicHelperImpl: ApiMusicHelperImpl): ApiMusicHelper =
        apiMusicHelperImpl
}