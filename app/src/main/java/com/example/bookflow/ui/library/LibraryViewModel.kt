package com.example.bookflow.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    private var observeBooksJob: Job? = null
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        observeBooks()
    }

    private fun observeBooks() {
        observeBooksJob?.cancel()

        observeBooksJob = viewModelScope.launch {
            bookRepository.getAllBooks()
                .catch {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            hasError = true
                        )
                    }
                }
                .collect { books ->
                    _uiState.update {
                        it.copy(
                            books = books,
                            isLoading = false,
                            hasError = false
                        )
                    }
                }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(
                selectedTabIndex = index
            )
        }
    }

    fun onLayoutChanged(isGridView: Boolean) {
        _uiState.update {
            it.copy(isGridView = isGridView)
        }
    }

    fun retryLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
                hasError = false
            )
        }

        observeBooks()
    }
}