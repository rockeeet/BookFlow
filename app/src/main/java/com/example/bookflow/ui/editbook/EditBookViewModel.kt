package com.example.bookflow.ui.editbook

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class EditBookViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val bookId: Int = checkNotNull(savedStateHandle["bookId"]) {
        "Book ID is missing"
    }

    private val _uiState = MutableStateFlow(EditBookUiState())
    val uiState = _uiState.asStateFlow()

    private var originalBook: Book? = null

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            try {
                val book = bookRepository.getBookById(bookId).first()

                if (book == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            hasLoadError = true
                        )
                    }
                    return@launch
                }

                originalBook = book

                _uiState.update {
                    it.copy(
                        title = book.title,
                        author = book.author,
                        description = book.description.orEmpty(),
                        totalPages = book.totalPages?.toString().orEmpty(),
                        publicationYear = book.publicationYear?.toString().orEmpty(),
                        coverUri = book.coverUrl,
                        status = book.status,
                        isLoading = false,
                        hasLoadError = false
                    )
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasLoadError = true
                    )
                }
            }
        }
    }


    fun saveChanges() {
        if (_uiState.value.isSaving) return

        val book = originalBook ?: return

        if (!validateForm()) return

        val state = _uiState.value

        val updatedBook = book.copy(
            title = state.title.trim(),
            author = state.author.trim(),
            description = state.description
                .trim()
                .takeIf { it.isNotEmpty() },
            totalPages = state.totalPages.toIntOrNull(),
            publicationYear = state.publicationYear.toIntOrNull(),
            coverUrl = state.coverUri,
            status = state.status
        )

        _uiState.update {
            it.copy(
                isSaving = true,
                hasSaveError = false
            )
        }

        viewModelScope.launch {
            try {
                bookRepository.updateBook(updatedBook)

                _uiState.update {
                    it.copy(
                        isSaving = false,
                        isSaved = true
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        hasSaveError = true
                    )
                }
            }
        }
    }



    fun onTitleChange(value: String) {
        _uiState.update {
            it.copy(
                title = value,
                titleError = if (it.showErrors) value.isBlank() else it.titleError
            )
        }
    }

    fun onAuthorChange(value: String) {
        _uiState.update {
            it.copy(
                author = value,
                authorError = if (it.showErrors) value.isBlank() else it.authorError
            )
        }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update {
            it.copy(description = value)
        }
    }

    fun onTotalPagesChange(value: String) {
        _uiState.update {
            it.copy(
                totalPages = value,
                pagesError = if (it.showErrors) {
                    value.isNotBlank() && (value.toIntOrNull()?.let { pages -> pages > 0 } != true)
                } else {
                    it.pagesError
                }
            )
        }
    }

    fun onPublicationYearChange(value: String) {
        val currentYear = java.util.Calendar.getInstance()
            .get(java.util.Calendar.YEAR)

        _uiState.update {
            it.copy(
                publicationYear = value,
                yearError = if (it.showErrors) {
                    value.isNotBlank() && (value.toIntOrNull()?.let { year ->
                        year in 1..currentYear
                    } != true)
                } else {
                    it.yearError
                }
            )
        }
    }

    fun onStatusChange(status: ReadingStatus) {
        _uiState.update {
            it.copy(status = status)
        }
    }

    fun onCoverSelected(uri: String) {
        _uiState.update {
            it.copy(coverUri = uri)
        }
    }

    private fun validateForm(): Boolean {
        val currentYear = java.util.Calendar.getInstance()
            .get(java.util.Calendar.YEAR)

        val state = _uiState.value

        val titleError = state.title.isBlank()
        val authorError = state.author.isBlank()

        val pagesError = state.totalPages.isNotBlank() &&
                (state.totalPages.toIntOrNull()?.let { it > 0 } != true)

        val yearError = state.publicationYear.isNotBlank() &&
                (state.publicationYear.toIntOrNull()?.let {
                    it in 1..currentYear
                } != true)

        _uiState.update {
            it.copy(
                showErrors = true,
                titleError = titleError,
                authorError = authorError,
                pagesError = pagesError,
                yearError = yearError
            )
        }

        return !titleError && !authorError && !pagesError && !yearError
    }

}
