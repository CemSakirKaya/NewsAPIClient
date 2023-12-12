package com.example.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.usecase.DeleteSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.example.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.example.newsapiclient.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsViewModel( private val app :Application,
                     private val newsHeadlinesUseCase: GetNewsHeadlinesUseCase,
                     private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
                     private val saveNewsUseCase: SaveNewsUseCase,
                     private val getSavedNewsUseCase: GetSavedNewsUseCase,
                     private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase) : AndroidViewModel(app){

     val newsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()



    fun getNewsHeadlines(country:String,page:Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (isNetworkAvailable(app)){
                newsHeadlines.postValue(Resource.Loading())
                val  apiResult = newsHeadlinesUseCase.execute(country,page)
                newsHeadlines.postValue(apiResult)
            }else{
                newsHeadlines.postValue(Resource.Error("Internet is not available"))
            }


        }catch (e:Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }


    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }


    //search

    val searchedNews : MutableLiveData<Resource<APIResponse>>  = MutableLiveData()

    fun searchNews(
        country: String,
        page: Int,
        searchQuery:String
    )= viewModelScope.launch {

        try {
            searchedNews.postValue(Resource.Loading())
            if (isNetworkAvailable(app) ){
                val response = getSearchedNewsUseCase.execute(
                    searchQuery,country,page
                )

                searchedNews.postValue(response)

            }else{
                searchedNews.postValue(Resource.Error("No internet connection"))
            }

        }
        catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }


    //local data

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)

    }





}