package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country:String,page:Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlinesResponse(page,country)
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Response<APIResponse> {
        return newsAPIService.getSearchedTopHeadlinesResponse(page,searchQuery,country)
    }


}