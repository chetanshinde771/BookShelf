package com.csapps.bookshelf.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.data.local_db.dao.BooksAllocationDao
import javax.inject.Inject

class BooksAllocationRepository @Inject constructor(
    private val booksAllocationDao: BooksAllocationDao) {

    val booksAllocation:LiveData<List<UserBookAllocation>> =
        booksAllocationDao.getAllAllocations()


    @WorkerThread
    suspend fun insertAllocation(bookAllocation: UserBookAllocation){
        booksAllocationDao.insertBookAllocation(bookAllocation)
    }

    @WorkerThread
    suspend fun updateAllocation(bookAllocation: UserBookAllocation){
        booksAllocationDao.updateAllocationDetails(bookAllocation.mappingId, bookAllocation.bookName,
            bookAllocation.userName, bookAllocation.userMobile)
    }

    @WorkerThread
    suspend fun deleteAllocation(id: Int){
        booksAllocationDao.deleteAllocation(id)
    }

    fun getAllocationDetails(allocationId: Int): LiveData<UserBookAllocation>{
        return booksAllocationDao.getAllocation(allocationId)
    }
}