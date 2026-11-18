package com.example.bookflow.ui.addbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.coroutines.cancellation.CancellationException

class AddBookViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun validateForm(): Boolean {
        val state = _uiState.value

        val currentYear = Calendar.getInstance()
            .get(Calendar.YEAR)

        val titleError = state.title.isBlank()
        val authorError = state.author.isBlank()

        val pagesError = hasPagesError(state.totalPages)
        val yearError = hasYearError(state.publicationYear)

        _uiState.update {
            it.copy(
                showErrors = true,
                titleError = titleError,
                authorError = authorError,
                pagesError = pagesError,
                yearError = yearError
            )
        }

        return !titleError &&
                !authorError &&
                !pagesError &&
                !yearError
    }

    fun onTitleChanged(value: String) {
        _uiState.update {
            it.copy(
                title = value,
                titleError = it.showErrors && value.isBlank()
            )
        }
    }

    fun onAuthorChanged(value: String) {
        _uiState.update {
            it.copy(
                author = value,
                authorError = it.showErrors && value.isBlank()
            )
        }
    }

    fun onDescriptionChanged(value: String) {
        _uiState.update {
            it.copy(description = value)
        }
    }

    fun onTotalPagesChanged(value: String) {
        _uiState.update {
            it.copy(
                totalPages = value,
                pagesError = it.showErrors && hasPagesError(value)
            )
        }
    }

    fun onPublicationYearChanged(value: String) {
        _uiState.update {
            it.copy(
                publicationYear = value,
                yearError = it.showErrors && hasYearError(value)
            )
        }
    }

    fun onCoverSelected(uri: String?) {
        _uiState.update {
            it.copy(coverUri = uri)
        }
    }

    fun saveBook() {
        if (_uiState.value.isSaving) return
        if (!validateForm()) return

        val state = _uiState.value

        val book = Book(
            id = 0,
            title = state.title.trim(),
            author = state.author.trim(),
            coverUrl = state.coverUri,
            publicationYear = state.publicationYear.toIntOrNull(),
            totalPages = state.totalPages.toIntOrNull(),
            description = state.description
                .trim()
                .takeIf { it.isNotEmpty() },
            status = ReadingStatus.WANT_TO_READ,
            currentPage = 0
        )

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    hasSaveError = false
                )
            }

            try {
                bookRepository.insertBook(book)

                _uiState.update {
                    it.copy(isSaving = false, isSaved = true)
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(isSaving = false, hasSaveError = true)
                }
            }
        }
    }

    private fun hasPagesError(value: String): Boolean {
        if (value.isBlank()) return false

        val pages = value.toIntOrNull()
        return pages == null || pages <= 0
    }

    private fun hasYearError(value: String): Boolean {
        if (value.isBlank()) return false

        val year = value.toIntOrNull()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        return year == null || year !in 1..currentYear
    }
}