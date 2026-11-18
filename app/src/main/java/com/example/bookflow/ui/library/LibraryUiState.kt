package com.example.bookflow.ui.library

import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus

data class LibraryUiState(
    val books: List<Book> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isGridView: Boolean = true,
    val isLoading: Boolean = true,
    val hasError: Boolean = false
) {
    val filteredBooks: List<Book>
        get() = when (selectedTabIndex) {
            1 -> books.filter { it.status == ReadingStatus.WANT_TO_READ }
            2 -> books.filter { it.status == ReadingStatus.CURRENTLY_READING }
            3 -> books.filter { it.status == ReadingStatus.COMPLETED }
            else -> books
        }

    val totalBooks: Int
        get() = books.size

    val completedBooks: Int
        get() = books.count { it.status == ReadingStatus.COMPLETED }

    val currentlyReadingBooks: Int
        get() = books.count { it.status == ReadingStatus.CURRENTLY_READING }
}
