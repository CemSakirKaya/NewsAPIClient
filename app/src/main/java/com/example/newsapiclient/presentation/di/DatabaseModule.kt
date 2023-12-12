package com.example.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newsapiclient.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesNewsDatabase(app:Application):ArticleDatabase
    = Room.databaseBuilder(app,ArticleDatabase::class.java,"news_db")
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun providesNewsDao(articleDatabase: ArticleDatabase)= articleDatabase.getDao()

}