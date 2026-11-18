package com.example.bookflow.ui.progress

import com.example.bookflow.data.model.Book

data class ReadingProgressUiState(
    val book: Book? = null,
    val currentPageInput: String = "",
    val isLoading: Boolean = true,
    val hasError: Boolean = false,

    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val hasSaveError: Boolean = false
) {
    val enteredPage: Int?
        get() = currentPageInput.toIntOrNull()

    val maxPages: Int?
        get() = book?.totalPages?.takeIf { it > 0 }

    val displayedPage: Int
        get() = (enteredPage ?: 0)
            .coerceAtLeast(0)
            .let { page ->
                maxPages?.let { page.coerceAtMost(it) } ?: page
            }

    val canSave: Boolean
        get() {
            val page = enteredPage
            val max = maxPages

            return page != null &&
                    page >= 0 &&
                    (max == null || page <= max)
        }
}
