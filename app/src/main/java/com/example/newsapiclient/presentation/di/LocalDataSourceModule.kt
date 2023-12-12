package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.db.ArticleDao
import com.example.newsapiclient.data.repository.datasource.NewLocalDataSource
import com.example.newsapiclient.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {


    @Singleton
    @Provides
    fun providesNewsLocalDataSource(dao: ArticleDao):NewLocalDataSource= NewsLocalDataSourceImpl(dao)

}