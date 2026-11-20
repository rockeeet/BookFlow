package com.example.bookflow.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookflow.data.model.ReadingStatus
import com.example.bookflow.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    init {
        observeBooks()
    }

    private fun observeBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooks()
                .catch {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        hasError = true
                    )
                }
                .collect { books ->

                    val totalBooks = books.size

                    val completedBooks = books.count {
                        it.status == ReadingStatus.COMPLETED
                    }

                    val currentlyReadingBooks = books.count {
                        it.status == ReadingStatus.CURRENTLY_READING
                    }

                    val wantToReadBooks = books.count {
                        it.status == ReadingStatus.WANT_TO_READ
                    }

                    val pagesRead = books.sumOf {
                        it.currentPage.coerceAtLeast(0)
                    }

                    _uiState.value = StatisticsUiState(
                        totalBooks = totalBooks,
                        completedBooks = completedBooks,
                        currentlyReadingBooks = currentlyReadingBooks,
                        wantToReadBooks = wantToReadBooks,
                        pagesRead = pagesRead,

                        completedProgress = calculateProgress(
                            completedBooks,
                            totalBooks
                        ),
                        currentlyReadingProgress = calculateProgress(
                            currentlyReadingBooks,
                            totalBooks
                        ),
                        wantToReadProgress = calculateProgress(
                            wantToReadBooks,
                            totalBooks
                        ),

                        completedProgressPercent = (calculateProgress(
                            completedBooks,
                            totalBooks
                        ) * 100).toInt(),

                        currentlyReadingProgressPercent = (calculateProgress(
                            currentlyReadingBooks,
                            totalBooks
                        ) * 100).toInt(),

                        wantToReadProgressPercent = (calculateProgress(
                            wantToReadBooks,
                            totalBooks
                        ) * 100).toInt(),

                        isLoading = false,
                        hasError = false
                    )
                }
        }
    }

    private fun calculateProgress(
        count: Int,
        total: Int
    ): Float {
        return if (total > 0) {
            count.toFloat() / total
        } else {
            0f
        }
    }
}
