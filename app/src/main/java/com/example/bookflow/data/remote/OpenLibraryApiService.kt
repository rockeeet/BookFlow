package com.example.bookflow.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String? = null,
        @Query("author") author: String? = null
    ): OpenLibrarySearchResponseDto
}