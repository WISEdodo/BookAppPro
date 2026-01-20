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
) : ListAdapter<BookItem, HistoryBookAdapter.HistoryViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemBookHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        // Only bind non-null items
        getItem(position)?.let { holder.bind(it) }
    }

    inner class HistoryViewHolder(private val binding: ItemBookHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookItem) {
            // Safe calls and elvis operators to prevent NullPointerExceptions
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
