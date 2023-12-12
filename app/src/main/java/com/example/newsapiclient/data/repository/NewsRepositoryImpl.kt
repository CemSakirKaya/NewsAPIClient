package com.example.newsapiclient.data.repository

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewLocalDataSource
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewLocalDataSource
):NewsRepository {
    override suspend fun getNewsHeadlines(country:String,page:Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country,page))
    }

    override suspend fun getSearchedNews(
        searchQuery: String,
        country: String,
        page: Int
    ): Resource<APIResponse> {

        return responseToResource(
           newsRemoteDataSource.getSearchedNews(country,page,searchQuery)
        )

    }


    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticleFromDB(article)
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)

    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getAllArticles()
    }



    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


}