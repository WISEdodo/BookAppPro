package com.example.bookapppro.utils

import android.content.Context
import android.content.SharedPreferences
import models.BookItem // We will create this model in Phase 3
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object UserPreferences {

    private const val PREFS_NAME = "BookAppProPrefs"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PASSWORD = "user_password"
    private const val KEY_HISTORY = "browsing_history"

    // Helper to get SharedPreferences instance
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Authentication ---

    /**
     * Saves user credentials. This is a simple implementation for
     * a single user. In a real app, you'd use a database.
     */
    fun saveUser(context: Context, email: String, password: String) {
        val editor = getPrefs(context).edit()
        editor.putString(KEY_USER_EMAIL, email)
        editor.putString(KEY_USER_PASSWORD, password) // Note: Storing plain passwords is not secure.
        editor.apply()
    }

    /**
     * Validates the entered credentials against the saved ones.
     */
    fun validateUser(context: Context, email: String, password: String): Boolean {
        val prefs = getPrefs(context)
        val savedEmail = prefs.getString(KEY_USER_EMAIL, null)
        val savedPassword = prefs.getString(KEY_USER_PASSWORD, null)
        return savedEmail == email && savedPassword == password
    }

    /**
     * Checks if a user is already "logged in" (i.e., credentials are saved).
     */
    fun isUserLoggedIn(context: Context): Boolean {
        val prefs = getPrefs(context)
        return prefs.contains(KEY_USER_EMAIL)
    }

    /**
     * Logs the user out by clearing their saved credentials.
     */
    fun logoutUser(context: Context) {
        val editor = getPrefs(context).edit()
        editor.remove(KEY_USER_EMAIL)
        editor.remove(KEY_USER_PASSWORD)
        editor.apply()
    }

    // --- Browsing History ---
    // (We add these now, even though we'll use them in a later phase)

    /**
     * Retrieves the list of browsed books.
     * Uses Gson to convert JSON string back to a List.
     */
    fun getHistory(context: Context): MutableList<BookItem> {
        val prefs = getPrefs(context)
        val gson = Gson()
        val json = prefs.getString(KEY_HISTORY, null)

        // If no history, return an empty list
        if (json == null) {
            return mutableListOf()
        }

        // Define the type of list Gson should expect
        val type = object : TypeToken<MutableList<BookItem>>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * Adds a book to the history.
     * Uses Gson to convert the List to a JSON string for storage.
     */
    fun saveToHistory(context: Context, book: BookItem) {
        val history = getHistory(context)

        // Avoid duplicates - remove if already exists, then add to top
        history.removeAll { it.id == book.id }
        history.add(0, book) // Add to the beginning of the list

        // Keep history to a reasonable size, e.g., 20 items
        while (history.size > 20) {
            history.removeAt(history.size - 1)
        }

        // Save the updated list back to SharedPreferences
        val gson = Gson()
        val json = gson.toJson(history)
        getPrefs(context).edit().putString(KEY_HISTORY, json).apply()
    }
}