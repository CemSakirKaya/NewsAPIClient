package com.example.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.example.newsapiclient.domain.usecase.SaveNewsUseCase

class NewsViewModelFactory(private val app : Application,
                           private val newsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                           private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
                           private val saveNewsUseCase: SaveNewsUseCase,
                           private val getSavedNewsUseCase: GetSavedNewsUseCase,
                           private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase)
    :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app,newsHeadlinesUseCase,getSearchedNewsUseCase,
            saveNewsUseCase,getSavedNewsUseCase,deleteSavedNewsUseCase) as T
    }


}