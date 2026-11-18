package com.example.bookflow.data.repository

import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.data.model.SearchType

interface DiscoverRepository {
    suspend fun searchBooks(
        query: String,
        type: SearchType
    ): List<SearchResult>
}