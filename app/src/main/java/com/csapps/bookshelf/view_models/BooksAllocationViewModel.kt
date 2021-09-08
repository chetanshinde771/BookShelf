package com.csapps.bookshelf.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.data.repository.BooksAllocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class BooksAllocationViewModel @Inject constructor(
    private val booksAllocationRepository: BooksAllocationRepository
    ): ViewModel() {

    val booksAllocationList = booksAllocationRepository.booksAllocation

    /*launching a coroutine to do background task*/
    fun insertAllocation(bookAllocation: UserBookAllocation): Boolean {
        var isCompleted = false

        /*waiting for the result*/
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch{
                booksAllocationRepository.insertAllocation(bookAllocation)
            }
            job.join()
            isCompleted = true
        }
        return isCompleted
    }

    fun updateAllocation(bookAllocation: UserBookAllocation): Boolean {
        var isCompleted = false
        runBlocking {
            val job = CoroutineScope(Dispatchers.IO).launch {
                booksAllocationRepository.updateAllocation(bookAllocation)
            }
            job.join()
            isCompleted = true
        }
        return isCompleted
    }

    fun deleteAllocation(mappingId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            booksAllocationRepository.deleteAllocation(mappingId)
        }
    }

    fun getBookAllocationDetails(allocationId: Int): LiveData<UserBookAllocation> {
        return booksAllocationRepository.getAllocationDetails(allocationId)
    }
}