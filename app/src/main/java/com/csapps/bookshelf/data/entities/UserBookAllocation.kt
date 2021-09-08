package com.csapps.bookshelf.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_books_allocation")
data class UserBookAllocation(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mapping_id") val mappingId: Int=0,
    /*@ColumnInfo(name = "book_id") var bookId: Int,*/
    @ColumnInfo(name = "book_name") var bookName: String,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo(name = "user_mobile") var userMobile: String,
)
