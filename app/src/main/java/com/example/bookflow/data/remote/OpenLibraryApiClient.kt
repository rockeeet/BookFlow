package com.example.bookflow.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenLibraryApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openlibrary.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: OpenLibraryApiService =
        retrofit.create(OpenLibraryApiService::class.java)
}