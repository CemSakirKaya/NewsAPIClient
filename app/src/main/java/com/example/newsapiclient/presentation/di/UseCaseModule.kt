package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.domain.repository.NewsRepository
import com.example.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.example.newsapiclient.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetNewsHeadlinesUseCase(repository: NewsRepository) : GetNewsHeadlinesUseCase
    = GetNewsHeadlinesUseCase(repository)


    @Provides
    @Singleton
    fun providesGetSavedNewsHeadlinesUseCase(repository: NewsRepository) : GetSearchedNewsUseCase
            = GetSearchedNewsUseCase(repository)

    @Provides
    @Singleton
    fun providesSaveNewsUseCase(repository: NewsRepository):SaveNewsUseCase
    = SaveNewsUseCase(repository)


    @Provides
    @Singleton
    fun providesGetSavedNewsUseCase(repository: NewsRepository):GetSavedNewsUseCase
    = GetSavedNewsUseCase(repository)


    @Provides
    @Singleton
    fun providesDeleteSavedNewsUseCase(repository: NewsRepository):DeleteSavedNewsUseCase
    = DeleteSavedNewsUseCase(repository)


}