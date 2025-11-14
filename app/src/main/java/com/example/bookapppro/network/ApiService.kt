package com.example.bookapppro.network

import com.example.bookapppro.models.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * This function will be called to search for books.
     * The @GET("volumes") part is appended to the Base URL.
     * The @Query("q") part adds a query parameter like: .../volumes?q=kotlin
     */
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}