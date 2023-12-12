package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.BuildConfig
import com.example.newsapiclient.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {


    @Singleton
    @Provides
    fun providesRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun providesNewsAPIService(retrofit: Retrofit) = retrofit.create(NewsAPIService::class.java)






}