package com.example.bookflow.ui.addbook

data class AddBookUiState(
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val totalPages: String = "",
    val publicationYear: String = "",
    val coverUri: String? = null,
    val showErrors: Boolean = false,

    val titleError: Boolean = false,
    val authorError: Boolean = false,
    val pagesError: Boolean = false,
    val yearError: Boolean = false,

    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val hasSaveError: Boolean = false
)
