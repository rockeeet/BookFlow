package com.example.bookflow.ui.editbook

import com.example.bookflow.data.model.ReadingStatus

data class EditBookUiState(
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val totalPages: String = "",
    val publicationYear: String = "",
    val coverUri: String? = null,
    val showErrors: Boolean = false,
    val status: ReadingStatus = ReadingStatus.WANT_TO_READ,

    val titleError: Boolean = false,
    val authorError: Boolean = false,
    val pagesError: Boolean = false,
    val yearError: Boolean = false,

    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val hasLoadError: Boolean = false,
    val hasSaveError: Boolean = false
)
