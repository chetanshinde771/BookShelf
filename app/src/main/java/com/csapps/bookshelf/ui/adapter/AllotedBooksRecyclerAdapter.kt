package com.csapps.bookshelf.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csapps.bookshelf.data.entities.UserBookAllocation
import com.csapps.bookshelf.databinding.AllocatedBookSingleItemBinding

class AllotedBooksRecyclerAdapter(var context: Context,
               var allocationList: List<UserBookAllocation>,
               private var removeBorrowerListener: RemoveBorrowerListener,
               private var changeBorrowerListener: ChangeBorrowerListener): RecyclerView.Adapter<AllotedBooksRecyclerAdapter.BooksViewHolder>() {


      class BooksViewHolder(private val itemBinding: AllocatedBookSingleItemBinding): RecyclerView.ViewHolder(itemBinding.root){
          fun bind(
              allocationData: UserBookAllocation,
              removeBorrowerListener: RemoveBorrowerListener,
              changeBorrowerListener: ChangeBorrowerListener, adapterPosition: Int){

              itemBinding.allocationDetails = allocationData
              itemBinding.removeBorrowerListener = removeBorrowerListener
              itemBinding.changeBorrowerListener = changeBorrowerListener
              itemBinding.adapterPosition = adapterPosition
              itemBinding.executePendingBindings()
          }
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = AllocatedBookSingleItemBinding.inflate(inflater)

        return BooksViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
       holder.bind(allocationList[position], removeBorrowerListener, changeBorrowerListener, position)
    }

    override fun getItemCount(): Int = allocationList.size

    interface RemoveBorrowerListener{
        fun onRemove(id: Int, adapterPosition: Int)
    }

    interface ChangeBorrowerListener{
        fun onChange(allocationData: UserBookAllocation)
    }
}