package com.csapps.bookshelf.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.csapps.bookshelf.R
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.databinding.ActivityAllocatedBooksBinding
import com.csapps.bookshelf.ui.adapter.AllotedBooksRecyclerAdapter
import com.csapps.bookshelf.view_models.BooksAllocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_allocated_books.*
import javax.inject.Inject

@AndroidEntryPoint
class AllocatedBooksActivity : AppCompatActivity() {

    /*injecting divider dependency*/
    @Inject
    lateinit var decoration: DividerItemDecoration

    /*initialising view model lazily*/
    private val booksAllocationViewModel by viewModels<BooksAllocationViewModel>()

    private var adapter: AllotedBooksRecyclerAdapter? = null
    private var allocationList: List<UserBookAllocation>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val binding: ActivityAllocatedBooksBinding =
             DataBindingUtil.setContentView(this, R.layout.activity_allocated_books)

        allotedBooksRecyclerView.addItemDecoration(decoration)

        booksAllocationViewModel.booksAllocationList.observe(this, Observer {
            if(it.isNotEmpty()) {
                allocationList = it
                binding.isDataAvailable = true
                setAdapter()
            }
            else {
                allocationList = it
                binding.isDataAvailable = false
            }

        })
    }

    fun setAdapter() {
        adapter = AllotedBooksRecyclerAdapter(this, allocationList!!, object:
                AllotedBooksRecyclerAdapter.RemoveBorrowerListener{
                    override fun onRemove(id: Int, adapterPosition: Int) {
                        booksAllocationViewModel.deleteAllocation(id)
                        adapter?.notifyItemRemoved(adapterPosition)
                    }
                }, object: AllotedBooksRecyclerAdapter.ChangeBorrowerListener{
                        override fun onChange(allocationData: UserBookAllocation) {
                            openUpdateScreen(allocationData)
                        }
                })
        allotedBooksRecyclerView.adapter = adapter
        allotedBooksRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    fun openUpdateScreen(allocationData: UserBookAllocation) {
        var updateIntent = Intent(this, BorrowBookActivity::class.java)
        updateIntent.apply {
            putExtra("allocationId", allocationData.mappingId)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(updateIntent)
    }

    fun showAddBorrowerScreen(view: View){
        var addIntent = Intent(this, BorrowBookActivity::class.java)
        addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(addIntent)
    }
}