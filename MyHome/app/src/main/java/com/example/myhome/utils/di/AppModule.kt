package com.example.myhome.utils.di

import android.content.Context
import com.example.myhome.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideString(): String {
        return "Hello I am Duc"
    }

    @Singleton
    @Provides
    fun getApplication(@ApplicationContext context: Context) : MyApplication {
        return context as MyApplication
    }
}