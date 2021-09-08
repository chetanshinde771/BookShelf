package com.csapps.bookshelf.di

import android.content.Context
import android.widget.Toast
import com.csapps.bookshelf.R
import com.csapps.bookshelf.data.local_db.BooksDatabase
import com.csapps.bookshelf.data.local_db.dao.BooksAllocationDao
import com.csapps.bookshelf.data.repository.BooksAllocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBooksDataBase(
        @ApplicationContext context: Context): BooksDatabase{

        return BooksDatabase.getDataBase(context)
    }

    @Singleton
    @Provides
    fun provideBooksAllocationDao(booksDatabase: BooksDatabase): BooksAllocationDao{
        return booksDatabase.bookAllocationDao()
    }

    @Singleton
    @Provides
    fun provideBooksAllocationRepository(
        booksAllocationDao: BooksAllocationDao): BooksAllocationRepository{

        return BooksAllocationRepository(booksAllocationDao)
    }

}