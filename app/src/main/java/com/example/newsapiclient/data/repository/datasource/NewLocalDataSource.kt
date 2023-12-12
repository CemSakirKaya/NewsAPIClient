package com.example.newsapiclient.data.repository.datasource

import com.example.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewLocalDataSource {

    suspend fun saveArticleToDB(article: Article)
     fun getAllArticles(): Flow<List<Article>>

     suspend fun deleteArticleFromDB(article: Article)
}