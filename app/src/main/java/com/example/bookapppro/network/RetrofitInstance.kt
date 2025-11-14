package com.example.bookapppro.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // The base URL for the Google Books API
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    /**
     * Create a lazy-initialized Retrofit instance.
     * This means it's only created once, the first time it's needed.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
            .build()
    }

    /**
     * Create a public 'api' object that other parts of the app
     * can use to access the ApiService.
     */
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}