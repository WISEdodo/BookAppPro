package com.example.bookapppro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookapppro.adapters.HistoryBookAdapter
import com.example.bookapppro.adapters.SavedBookAdapter
import com.example.bookapppro.adapters.SearchBookAdapter
import com.example.bookapppro.databinding.ActivityMainBinding
import com.example.bookapppro.models.BookItem
import com.example.bookapppro.network.RetrofitInstance
import com.example.bookapppro.utils.UserPreferences
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchAdapter: SearchBookAdapter
    private lateinit var historyAdapter: HistoryBookAdapter
    private lateinit var savedAdapter: SavedBookAdapter
    private lateinit var userPreferences: UserPreferences
    private var lastSearchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvEmptyState.text = "Use the search bar to find books."
        binding.tvEmptyState.visibility = View.VISIBLE

        setSupportActionBar(binding.toolbar)
        userPreferences = UserPreferences(this)
        setupRecyclerViews()
        setupSearchBar()
        setupChips()
    }

    override fun onResume() {
        super.onResume()
        loadHistory()
        loadSavedBooks()
    }

    private fun setupRecyclerViews() {
        searchAdapter = SearchBookAdapter { book ->
            navigateToDetail(book)
        }
        binding.searchResultsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = searchAdapter
        }

        historyAdapter = HistoryBookAdapter { book ->
            navigateToDetail(book)
        }
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }

        savedAdapter = SavedBookAdapter { book ->
            navigateToDetail(book)
        }
        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = savedAdapter
        }
    }

    private fun setupSearchBar() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    handleSearch(query)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupChips() {
        binding.chipTrending.setOnClickListener { handleSearch("Trending Books") }
        binding.chipKotlin.setOnClickListener { handleSearch("Kotlin Programming") }
        binding.chipFiction.setOnClickListener { handleSearch("Fiction Books") }
        binding.chipScience.setOnClickListener { handleSearch("Science") }
        binding.chipHistory.setOnClickListener { handleSearch("History") }
        binding.chipBiography.setOnClickListener { handleSearch("Biography") }
        binding.chipAuthors.setOnClickListener { handleSearch("Famous Authors") }
        binding.chipFantasy.setOnClickListener { handleSearch("Fantasy") }
        binding.chipMystery.setOnClickListener { handleSearch("Mystery") }
    }

    private fun handleSearch(query: String) {
        if (query == lastSearchQuery) {
            // If the same query is performed twice, clear the search
            searchAdapter.submitList(emptyList())
            lastSearchQuery = null
            binding.tvEmptyState.text = "Search cleared."
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            performSearch(query)
            lastSearchQuery = query
        }
    }

    private fun performSearch(query: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmptyState.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.searchBooks(query, BuildConfig.GOOGLE_BOOKS_API_KEY)
                binding.progressBar.visibility = View.GONE

                if (response.items.isNullOrEmpty()) {
                    binding.tvEmptyState.visibility = View.VISIBLE
                    binding.tvEmptyState.text = "No results found"
                    searchAdapter.submitList(emptyList())
                } else {
                    searchAdapter.submitList(response.items)
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Search failed", e)
                binding.progressBar.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "Error: ${e.message}"
            }
        }
    }

    private fun loadHistory() {
        val history = userPreferences.getHistory()
        historyAdapter.submitList(history)
        binding.historyRecyclerView.visibility = if (history.isEmpty()) View.GONE else View.VISIBLE
        binding.tvHistoryTitle.visibility = if (history.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun loadSavedBooks() {
        val savedBooks = userPreferences.getSavedBooks()
        savedAdapter.submitList(savedBooks)
        binding.savedRecyclerView.visibility = if (savedBooks.isEmpty()) View.GONE else View.VISIBLE
        binding.tvSavedTitle.visibility = if (savedBooks.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun navigateToDetail(book: BookItem) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("bookItem", book)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                userPreferences.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
