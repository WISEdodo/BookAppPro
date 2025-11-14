package com.example.bookapppro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookapppro.R
import com.example.bookapppro.databinding.ItemBookSearchBinding
import com.example.bookapppro.models.BookItem

class SearchBookAdapter(
    private val onItemClick: (BookItem) -> Unit
) : ListAdapter<BookItem, SearchBookAdapter.BookViewHolder>(DiffCallback) {

    /**
     * ViewHolder class for the book item.
     * Binds the data to the views in item_book_search.xml.
     */
    inner class BookViewHolder(private val binding: ItemBookSearchBinding) :
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
            val volumeInfo = bookItem.volumeInfo
            val imageUrl = volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")

            binding.tvBookTitle.text = volumeInfo?.title ?: "No Title"
            binding.tvBookDescription.text = volumeInfo?.description ?: "No Description"

            // Use Glide to load the image
            Glide.with(binding.ivBookCover.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // A placeholder
                .error(R.drawable.ic_launcher_background) // Fallback on error
                .into(binding.ivBookCover)
        }
    }

    /**
     * Creates new view holders (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            ItemBookSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    /**
     * DiffUtil helps the adapter efficiently update the list
     * by comparing new and old lists.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            // 'id' is a unique identifier for each book from the API
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            // Check if the content (e.g., title, image) is the same
            return oldItem == newItem
        }
    }
}