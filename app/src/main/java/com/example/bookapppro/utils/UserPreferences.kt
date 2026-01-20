package com.example.bookapppro.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.bookapppro.models.BookItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("BookAppPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val KEY_CURRENT_USER = "currentUser"
    private val HISTORY_PREFIX = "history_"
    private val SAVED_BOOKS_PREFIX = "saved_books_"

    fun saveUser(email: String, password: String) {
        prefs.edit().apply {
            // Save registration details
            putString("email", email)
            putString("password", password)
            // Immediately log the new user in
            putString(KEY_CURRENT_USER, email)
            apply()
        }
    }

    fun validateUser(email: String, password: String): Boolean {
        val savedEmail = prefs.getString("email", "")
        val savedPassword = prefs.getString("password", "")
        val isValid = email == savedEmail && password == savedPassword
        if (isValid) {
            prefs.edit().putString(KEY_CURRENT_USER, email).apply()
        }
        return isValid
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.contains(KEY_CURRENT_USER)
    }

    fun logout() {
        prefs.edit().remove(KEY_CURRENT_USER).apply()
    }

    fun addHistory(book: BookItem) {
        val currentUser = prefs.getString(KEY_CURRENT_USER, null) ?: return
        val historyKey = HISTORY_PREFIX + currentUser
        val history = getHistory().toMutableList()
        history.removeAll { it.id == book.id }
        history.add(0, book)
        if (history.size > 20) {
            history.subList(20, history.size).clear()
        }
        val json = gson.toJson(history)
        prefs.edit().putString(historyKey, json).apply()
    }

    fun getHistory(): List<BookItem> {
        val currentUser = prefs.getString(KEY_CURRENT_USER, null) ?: return emptyList()
        val historyKey = HISTORY_PREFIX + currentUser
        val json = prefs.getString(historyKey, null) ?: return emptyList()
        val type = object : TypeToken<List<BookItem>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            Log.e("UserPreferences", "Error parsing history JSON for user: $currentUser", e)
            clearHistoryForCurrentUser()
            emptyList()
        }
    }

    fun getSavedBooks(): List<BookItem> {
        val currentUser = prefs.getString(KEY_CURRENT_USER, null) ?: return emptyList()
        val savedKey = SAVED_BOOKS_PREFIX + currentUser
        val json = prefs.getString(savedKey, null) ?: return emptyList()
        val type = object : TypeToken<List<BookItem>>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            Log.e("UserPreferences", "Error parsing saved books JSON for user: $currentUser", e)
            prefs.edit().remove(savedKey).apply()
            emptyList()
        }
    }

    fun isBookSaved(book: BookItem): Boolean {
        return getSavedBooks().any { it.id == book.id }
    }

    fun saveBook(book: BookItem) {
        val savedBooks = getSavedBooks().toMutableList()
        if (!savedBooks.any { it.id == book.id }) {
            savedBooks.add(book)
            updateSavedBooks(savedBooks)
        }
    }

    fun unsaveBook(book: BookItem) {
        val savedBooks = getSavedBooks().toMutableList()
        savedBooks.removeAll { it.id == book.id }
        updateSavedBooks(savedBooks)
    }

    private fun updateSavedBooks(books: List<BookItem>) {
        val currentUser = prefs.getString(KEY_CURRENT_USER, null) ?: return
        val savedKey = SAVED_BOOKS_PREFIX + currentUser
        val json = gson.toJson(books)
        prefs.edit().putString(savedKey, json).apply()
    }

    private fun clearHistoryForCurrentUser() {
        val currentUser = prefs.getString(KEY_CURRENT_USER, null) ?: return
        val historyKey = HISTORY_PREFIX + currentUser
        prefs.edit().remove(historyKey).apply()
    }
}
