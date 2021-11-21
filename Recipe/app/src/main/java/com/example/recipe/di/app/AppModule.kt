package com.example.recipe.di.app

import com.example.recipe.data.api.food.ApiServiceFood
import com.example.recipe.data.constant.BASE_URL_FOOD
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiServiceFood(): ApiServiceFood {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_FOOD)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiServiceFood::class.java)
    }

}