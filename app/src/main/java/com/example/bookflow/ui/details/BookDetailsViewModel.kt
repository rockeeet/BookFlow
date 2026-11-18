package com.example.bookflow.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val bookId: Int? = savedStateHandle["bookId"]

    private val _uiState = MutableStateFlow(BookDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        bookId?.let { loadBook(it) }
    }

    private fun loadBook(bookId: Int) {
        viewModelScope.launch {
            try {
                bookRepository.getBookById(bookId).collect { book ->
                    _uiState.update {
                        it.copy(
                            book = book,
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

    fun loadSearchResult(searchResult: SearchResult) {
        viewModelScope.launch {
            try {
                bookRepository.getBookByExternalId(searchResult.id).collect { book ->
                    val displayBook = book ?: Book(
                        id = 0,
                        title = searchResult.title,
                        author = searchResult.author,
                        coverUrl = searchResult.coverUrl,
                        publicationYear = searchResult.firstPublishYear,
                        totalPages = null,
                        description = null,
                        status = ReadingStatus.WANT_TO_READ,
                        currentPage = 0,
                        externalId = searchResult.id
                    )

                    _uiState.update {
                        it.copy(
                            book = displayBook,
                            searchResult = if (book == null) searchResult else null,
                            isLoading = false,
                            hasError = false,
                            isFromDiscover = true,
                            isInLibrary = book != null
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

    fun addToLibrary() {
        val currentState = _uiState.value
        val book = currentState.book ?: return

        if (currentState.isInLibrary || currentState.isAddingToLibrary) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isAddingToLibrary = true,
                    hasAddError = false
                )
            }

            try {
                bookRepository.insertBook(book.copy(id = 0))

                _uiState.update {
                    it.copy(isAddingToLibrary = false)
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isAddingToLibrary = false,
                        hasAddError = true
                    )
                }
            }
        }
    }

    fun deleteBook() {
        val book = _uiState.value.book ?: return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDeleting = true,
                    isDeleted = false,
                    hasDeleteError = false
                )
            }

            try {
                bookRepository.deleteBook(book)

                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        isDeleted = true
                    )
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        hasDeleteError = true
                    )
                }
            }
        }
    }
}