package com.csapps.bookshelf.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.csapps.bookshelf.data.entities.BooksModel
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.data.local_db.dao.BooksAllocationDao


@Database(entities = arrayOf(BooksModel::class, UserBookAllocation::class),
    version = 1)
abstract class BooksDatabase: RoomDatabase() {

    abstract fun bookAllocationDao(): BooksAllocationDao

    companion object{

        @Volatile
        private var INSTANCE: BooksDatabase? = null

        fun getDataBase(context: Context): BooksDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context,
                BooksDatabase::class.java, "books_database")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}