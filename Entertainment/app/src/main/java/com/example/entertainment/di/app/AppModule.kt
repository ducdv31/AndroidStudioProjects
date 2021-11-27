package com.example.entertainment.di.app

import com.example.entertainment.data.BASE_URL_MOVIE
import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.repository.Repository
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
    fun provideApiMovie(): ApiServiceMovie = Retrofit.Builder()
        .baseUrl(BASE_URL_MOVIE)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiServiceMovie::class.java)
}