package com.example.bookflow.ui.discover

import com.example.bookflow.data.model.SearchResult

data class DiscoverUiState(
    val results: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val addedBookIds: Set<String> = emptySet(),
    val message: String? = null,
    val hasSearched: Boolean = false
)
