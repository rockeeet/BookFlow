package com.example.bookflow.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.model.SearchResult
import com.example.bookflow.data.model.SearchType
import com.example.bookflow.data.repository.BookRepository
import com.example.bookflow.data.repository.DiscoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val discoverRepository: DiscoverRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            bookRepository.getAllBooks()
                .map { books ->
                    books.mapNotNull { it.externalId }.toSet()
                }
                .collect { externalIds ->
                    _uiState.value = _uiState.value.copy(
                        addedBookIds = externalIds
                    )
                }
        }
    }

    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

    fun searchBooks(query: String, type: SearchType) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                hasSearched = true
            )

            try {
                val results = discoverRepository.searchBooks(query, type)

                _uiState.value = _uiState.value.copy(
                    results = results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun addBookToLibrary(result: SearchResult) {
        if (result.id in _uiState.value.addedBookIds) return

        viewModelScope.launch {
            try {
                // Проверяем, есть ли книга в локальной библиотеке
                val alreadyExists = bookRepository.existsByExternalId(result.id)

                if (alreadyExists) {
                    _uiState.value = _uiState.value.copy(
                        addedBookIds = _uiState.value.addedBookIds + result.id,
                        message = "This book is already in your library"
                    )
                    return@launch
                }

                val book = Book(
                    id = 0,
                    title = result.title,
                    author = result.author,
                    coverUrl = null,
                    publicationYear = result.firstPublishYear,
                    totalPages = null,
                    description = null,
                    status = ReadingStatus.WANT_TO_READ,
                    currentPage = 0,
                    externalId = result.id
                )

                bookRepository.insertBook(book)

                _uiState.value = _uiState.value.copy(
                    addedBookIds = _uiState.value.addedBookIds + result.id,
                    message = "Book added to your library!"
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = e.message ?: "Failed to add book"
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}