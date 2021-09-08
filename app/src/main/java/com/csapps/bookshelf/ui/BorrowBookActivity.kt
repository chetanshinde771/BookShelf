package com.csapps.bookshelf.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.csapps.bookshelf.R
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.util.ToastUtil
import com.csapps.bookshelf.view_models.BooksAllocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_borrow_book.*
import javax.inject.Inject

@AndroidEntryPoint
class BorrowBookActivity : AppCompatActivity() {

    @Inject
    lateinit var bookNames: Array<String>

    var selectedBook: String = ""
    var mappingId: Int = -1
    private val booksAllocationViewModel by viewModels<BooksAllocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrow_book)

        with(intent){
           val allocationId = getIntExtra("allocationId", -1)
            if(allocationId!=-1) {
                booksAllocationViewModel.getBookAllocationDetails(allocationId).observe(
                    this@BorrowBookActivity, Observer {
                    setUserData(it)
                })
            }
        }

        setDropDown()

        saveDetails.setOnClickListener(View.OnClickListener {
            /*check if all details are filled before storing in database*/
            if(isDetailsFilled()) {

                /*check if data needs to be updated or inserted*/
                if(mappingId==-1)
                    saveUserBookDetails()
                else
                    updateUserBookDetails()
            }
        })

        viewAllocations.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AllocatedBooksActivity::class.java))
        })
    }

    private fun setDropDown() {

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, bookNames)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        booksSpinner.adapter = adapter

        booksSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedBook = bookNames[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

   private fun isDetailsFilled():Boolean{
       if(booksSpinner.selectedItemPosition==0){
           ToastUtil.showToast(this,"Select a book")
           return false
       } else if(userName.text.isEmpty()) {
            ToastUtil.showToast(this, "Enter a name")
            return false
        }else if(userMobile.text.isEmpty()){
            ToastUtil.showToast(this,"Enter mobile number")
            return false
        }else if(userMobile.text.length<10){
            ToastUtil.showToast(this,"Enter valid mobile number")
            return false
        }
        else return true
    }

    fun setUserData(userBookAllocation: UserBookAllocation) {
        userName.setText(userBookAllocation.userName)
        userMobile.setText(userBookAllocation.userMobile)
        selectedBook = userBookAllocation.bookName
        mappingId = userBookAllocation.mappingId
        for((index, book) in bookNames.withIndex()){
            if(book == userBookAllocation.bookName)
                booksSpinner.setSelection(index)
        }
    }

    private fun updateUserBookDetails(){
        var allocation = UserBookAllocation(mappingId = mappingId, bookName = selectedBook,
            userName = userName.text.toString(), userMobile = userMobile.text.toString())

        val isJobCompleted = booksAllocationViewModel.updateAllocation(allocation)

        if(isJobCompleted){
            /*clearing fields*/
            clearFields()
            startActivity(Intent(this, AllocatedBooksActivity::class.java))
        }
    }

    private fun saveUserBookDetails() {
        var allocation = UserBookAllocation(bookName = selectedBook,
            userName = userName.text.toString(), userMobile = userMobile.text.toString())

        val isJobCompleted = booksAllocationViewModel.insertAllocation(allocation)

        if(isJobCompleted){
            /*clearing fields*/
            clearFields()
            startActivity(Intent(this, AllocatedBooksActivity::class.java))
        }
    }

    fun clearFields(){
        userName.text.clear()
        userMobile.text.clear()
        booksSpinner.setSelection(0)
        selectedBook = ""
        mappingId = -1
    }
}