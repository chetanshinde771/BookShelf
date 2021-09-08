package com.csapps.bookshelf.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books_master")
data class BooksModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id") val bookId: Int,
    @ColumnInfo(name = "name") var bookName: String?,
    @ColumnInfo(name = "author") var bookAuthor: String?,
    @ColumnInfo(name = "price") var bookPrice: String?,
    @ColumnInfo(name = "cover_photo") var bookCover: Int?, // for passing image resource id
)
