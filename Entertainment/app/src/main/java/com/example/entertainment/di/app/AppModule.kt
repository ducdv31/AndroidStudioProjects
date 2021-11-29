package com.example.entertainment.di.app

import com.example.entertainment.data.BASE_URL_BITCOIN
import com.example.entertainment.data.BASE_URL_MOVIE
import com.example.entertainment.data.BASE_URL_NEWS
import com.example.entertainment.data.BASE_URL_WEATHER
import com.example.entertainment.data.bitcoin.ApiServiceBitcoin
import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.news.ApiServiceNews
import com.example.entertainment.data.weather.ApiServiceWeather
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

    @Provides
    @Singleton
    fun provideApiBitcoin(): ApiServiceBitcoin = Retrofit.Builder()
        .baseUrl(BASE_URL_BITCOIN)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiServiceBitcoin::class.java)

    @Provides
    @Singleton
    fun provideApiWeather(): ApiServiceWeather = Retrofit.Builder()
        .baseUrl(BASE_URL_WEATHER)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiServiceWeather::class.java)

    @Provides
    @Singleton
    fun provideApiNews(): ApiServiceNews = Retrofit.Builder()
        .baseUrl(BASE_URL_NEWS)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiServiceNews::class.java)
}