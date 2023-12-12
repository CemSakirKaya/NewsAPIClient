package com.example.newsapiclient.presentation.di

import android.app.Application
import com.example.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.example.newsapiclient.domain.usecase.SaveNewsUseCase
import com.example.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
     fun   providesNewsViewModelFactory(application: Application,
                                        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                                        getSearchedNewsUseCase: GetSearchedNewsUseCase,
                                        saveNewsUseCase: SaveNewsUseCase,
                                        getSavedNewsUseCase: GetSavedNewsUseCase,
                                        deleteSavedNewsUseCase: DeleteSavedNewsUseCase):NewsViewModelFactory
     = NewsViewModelFactory(application,getNewsHeadlinesUseCase,getSearchedNewsUseCase
        ,saveNewsUseCase,getSavedNewsUseCase,deleteSavedNewsUseCase)


}