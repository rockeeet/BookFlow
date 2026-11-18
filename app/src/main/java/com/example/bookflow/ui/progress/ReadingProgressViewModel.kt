package com.example.bookflow.ui.progress

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReadingProgressViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val bookId: Int = checkNotNull(savedStateHandle["bookId"]) {
        "Book ID is missing"
    }

    private val _uiState = MutableStateFlow(ReadingProgressUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            try {
                bookRepository.getBookById(bookId).collect { book ->
                    _uiState.update { state ->
                        state.copy(
                            book = book,
                            currentPageInput = if (state.book == null && book != null) {
                                book.currentPage.toString()
                            } else {
                                state.currentPageInput
                            },
                            isLoading = false,
                            hasError = book == null
                        )
                    }
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
            }
        }
    }

    fun onCurrentPageChange(value: String) {
        if (value.isNotEmpty() && value.toIntOrNull() == null) return

        _uiState.update { state ->
            state.copy(
                currentPageInput = value
            )
        }
    }

    fun saveProgress() {
        val state = _uiState.value
        val book = state.book
        val currentPage = state.enteredPage

        if (book == null || currentPage == null || !state.canSave) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    isSaved = false,
                    hasSaveError = false
                )
            }

            try {
                bookRepository.updateBook(
                    book.copy(currentPage = currentPage)
                )

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

    fun incrementPage() {
        _uiState.update { state ->
            val currentPage = state.enteredPage ?: 0
            val maxPages = state.maxPages

            if (maxPages != null && currentPage >= maxPages) {
                state
            } else {
                state.copy(
                    currentPageInput = (currentPage + 1).toString()
                )
            }
        }
    }

    fun decrementPage() {
        _uiState.update { state ->
            val currentPage = state.enteredPage ?: 0

            if (currentPage <= 0) {
                state
            } else {
                state.copy(
                    currentPageInput = (currentPage - 1).toString()
                )
            }
        }
    }

    fun markAsCompleted() {
        val book = _uiState.value.book ?: return
        val totalPages = book.totalPages ?: return

        if (totalPages <= 0) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSaving = true,
                    isSaved = false,
                    hasSaveError = false
                )
            }

            try {
                bookRepository.updateBook(
                    book.copy(
                        currentPage = totalPages,
                        status = ReadingStatus.COMPLETED
                    )
                )

                _uiState.update {
                    it.copy(
                        currentPageInput = totalPages.toString(),
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
}
