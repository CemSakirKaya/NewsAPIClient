package com.example.newsapiclient.data.repository.datasourceImpl

import com.example.newsapiclient.data.db.ArticleDao
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.data.repository.datasource.NewLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val dao: ArticleDao):NewLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
         dao.insert(article)
    }

    override  fun getAllArticles(): Flow<List<Article>> {
     return  dao.getAllArticles()
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        dao.delete(article)
    }


}