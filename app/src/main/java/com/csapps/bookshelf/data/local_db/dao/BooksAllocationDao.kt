package com.csapps.bookshelf.data.local_db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.csapps.bookshelf.data.entities.UserBookAllocation

@Dao
interface BooksAllocationDao {

    @Query("SELECT * FROM user_books_allocation ORDER BY mapping_id DESC")
    fun getAllAllocations(): LiveData<List<UserBookAllocation>>

    @Query("SELECT * FROM user_books_allocation WHERE mapping_id =:allocationId")
    fun getAllocation(allocationId: Int): LiveData<UserBookAllocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookAllocation(bookAllocation: UserBookAllocation)

    @Query("DELETE FROM user_books_allocation")
    fun deleteAllAllocations()

    @Query("UPDATE user_books_allocation SET book_name = :bookName, user_name = :userName, user_mobile = :userMobile WHERE mapping_id = :mappingId")
    fun updateAllocationDetails(mappingId: Int, bookName: String, userName: String,
        userMobile: String)

    @Update
    fun updateAllocation(bookAllocation: UserBookAllocation)

    @Query("DELETE FROM user_books_allocation WHERE mapping_id = :mappingId")
    fun deleteAllocation(mappingId: Int)
}