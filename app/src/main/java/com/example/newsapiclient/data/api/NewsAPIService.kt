package com.example.newsapiclient.data.api

import com.example.newsapiclient.BuildConfig
import com.example.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface NewsAPIService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesResponse(
        @Query("page")
         page:Int,
        @Query("country")
        country:String,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ): Response<APIResponse>



    @GET("/v2/top-headlines")
    suspend fun getSearchedTopHeadlinesResponse(
        @Query("page")
        page:Int,
        @Query("q")
        searchQuery:String,
        @Query("country")
        country:String,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ): Response<APIResponse>


}