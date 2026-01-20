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
) : ListAdapter<BookItem, SearchBookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // Only bind non-null items
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BookViewHolder(private val binding: ItemBookSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookItem) {
            // Safe calls and elvis operators to prevent NullPointerExceptions
            binding.tvTitle.text = book.volumeInfo?.title ?: "No Title"
            binding.tvDescription.text = book.volumeInfo?.description ?: "No description available"

            val imageUrl = book.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")

            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_book_placeholder)
                .error(R.drawable.ic_book_placeholder)
                .into(binding.ivBookCover)

            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<BookItem>() {
        override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            // Use id for comparison, default to false if id is null
            return oldItem.id == newItem.id && oldItem.id != null
        }

        override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
            return oldItem == newItem
        }
    }
}
