package com.example.bookapppro.network

import com.example.bookapppro.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val client: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            // Set the log level to show everything
            logging.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(logging) // Add the logging interceptor
                .addInterceptor { chain ->
                    // Keep the no-cache header
                    val request = chain.request().newBuilder()
                        .header("Cache-Control", "no-cache")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
