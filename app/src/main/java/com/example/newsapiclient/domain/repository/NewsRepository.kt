package com.example.newsapiclient.domain.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country:String,page:Int):Resource<APIResponse>
    suspend fun getSearchedNews(searchQuery:String,country: String,page: Int):Resource<APIResponse>
    suspend fun deleteNews(article: Article)
    suspend fun saveNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}