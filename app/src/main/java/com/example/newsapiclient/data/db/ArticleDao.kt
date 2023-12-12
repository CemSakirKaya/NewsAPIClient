package com.example.newsapiclient.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)


    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>


    @Delete
    suspend fun delete(article: Article)





}