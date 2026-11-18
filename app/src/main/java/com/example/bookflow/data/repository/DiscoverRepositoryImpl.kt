package com.example.bookflow.data.repository

import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.data.model.SearchType
import com.example.bookflow.data.remote.OpenLibraryApiService

class DiscoverRepositoryImpl(
    private val api: OpenLibraryApiService
): DiscoverRepository {
    override suspend fun searchBooks(
        query: String,
        type: SearchType
    ): List<SearchResult> {
        val response = when (type) {
            SearchType.TITLE -> api.searchBooks(title = query)
            SearchType.AUTHOR -> api.searchBooks(author = query)
        }

        return response.books.orEmpty().mapNotNull { book ->
            val key = book.key ?: return@mapNotNull null
            val title = book.title ?: return@mapNotNull null

            SearchResult(
                id = key,
                title = title,
                author = book.authors?.joinToString(", ") ?: "Unknown author",
                firstPublishYear = book.firstPublishYear,
                coverUrl = book.coverId?.let { coverId ->
                    "https://covers.openlibrary.org/b/id/$coverId-M.jpg"
                }
            )
        }
    }
}