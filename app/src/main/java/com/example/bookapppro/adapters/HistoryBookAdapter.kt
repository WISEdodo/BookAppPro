package com.example.bookapppro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapppro.R
import com.example.bookapppro.databinding.ItemBookHistoryBinding
import com.example.bookapppro.models.BookItem

class HistoryBookAdapter(
    private val onItemClick: (BookItem) -> Unit
) : ListAdapter<BookItem, HistoryBookAdapter.HistoryViewHolder>(DiffCallback) {

    /**
     * ViewHolder class for the history item.
     * Binds the data to the views in item_book_history.xml.
     */
    inner class HistoryViewHolder(private val binding: ItemBookHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set the click listener on the item view
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { bookItem ->
                        onItemClick(bookItem)
                    }
                }
            }
        }

        fun bind(bookItem: BookItem) {
            val imageUrl = bookItem.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")

            // Use Glide to load the image
            Glide.with(binding.ivBookCoverHistory.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // A placeholder
                .error(R.drawable.ic_launcher_background) // Fallback on error
                .into(binding.ivBookCoverHistory)
        }
    }

    /**
     * Creates new view holders (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            ItemBookHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    /**
     * DiffUtil helps the adapter efficiently update the list.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            return oldItem == newItem
        }
    }
}