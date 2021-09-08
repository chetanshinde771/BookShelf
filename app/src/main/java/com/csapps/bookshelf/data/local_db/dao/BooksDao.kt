package com.csapps.bookshelf.data.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.csapps.bookshelf.data.entities.BooksModel

@Dao
interface BooksDao {

    @Query("SELECT * FROM books_master ORDER BY name ASC")
    fun getAllBooks(): LiveData<List<BooksModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookDetails(booksModel: BooksModel)

    @Query("DELETE FROM books_master")
    suspend fun deleteBooks()
}