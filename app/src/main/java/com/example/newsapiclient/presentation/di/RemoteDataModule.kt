package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapiclient.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
  fun  providesRemoteNewsDataSource(newsAPIService: NewsAPIService):NewsRemoteDataSource
  = NewsRemoteDataSourceImpl(newsAPIService)


}