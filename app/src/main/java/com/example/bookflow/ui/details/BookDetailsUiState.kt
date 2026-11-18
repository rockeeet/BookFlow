package com.example.bookflow.ui.details

import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.SearchResult

data class BookDetailsUiState(
    val book: Book? = null,
    val isLoading: Boolean = true,
    val hasError: Boolean = false,

    val isDeleting: Boolean = false,
    val isDeleted: Boolean = false,
    val hasDeleteError: Boolean = false,

    val isFromDiscover: Boolean = false,
    val isInLibrary: Boolean = true,
    val searchResult: SearchResult? = null,

    val isAddingToLibrary: Boolean = false,
    val hasAddError: Boolean = false
)
