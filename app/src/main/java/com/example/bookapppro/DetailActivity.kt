package com.example.bookapppro

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bookapppro.R
import com.example.bookapppro.databinding.ActivityDetailBinding
import com.example.bookapppro.models.BookItem
import com.example.bookapppro.utils.UserPreferences

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var userPreferences: UserPreferences
    private var currentBook: BookItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreferences = UserPreferences(this)

        currentBook = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("bookItem", BookItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("bookItem") as? BookItem
        }

        currentBook?.let {
            displayBookDetails(it)
            userPreferences.addHistory(it)
            setupSaveButton(it)
        } ?: run {
            binding.tvTitle.text = "Error"
            binding.tvDescription.text = "Could not load book details."
        }
    }

    private fun displayBookDetails(book: BookItem) {
        supportActionBar?.title = book.volumeInfo?.title ?: "Book Detail"
        binding.tvTitle.text = book.volumeInfo?.title ?: "No Title"
        binding.tvDescription.text = book.volumeInfo?.description ?: "No description available."
        binding.tvAuthors.text = book.volumeInfo?.authors?.joinToString(", ") ?: "Unknown Author"
        val imageUrl = book.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_book_placeholder)
            .error(R.drawable.ic_book_placeholder)
            .into(binding.ivBookCover)
    }

    private fun setupSaveButton(book: BookItem) {
        updateFabIcon(book)
        binding.fabSave.setOnClickListener {
            if (userPreferences.isBookSaved(book)) {
                userPreferences.unsaveBook(book)
            } else {
                userPreferences.saveBook(book)
            }
            updateFabIcon(book)
        }
    }

    private fun updateFabIcon(book: BookItem) {
        val icon = if (userPreferences.isBookSaved(book)) {
            R.drawable.ic_saved // "Filled" icon
        } else {
            R.drawable.ic_save // "Outline" icon
        }
        binding.fabSave.setImageDrawable(ContextCompat.getDrawable(this, icon))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
