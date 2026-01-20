package com.example.bookapppro.network

import com.example.bookapppro.models.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String, @Query("key") apiKey: String): BookResponse
}
