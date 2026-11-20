package com.example.bookflow.ui.statistics

data class StatisticsUiState(
    val totalBooks: Int = 0,
    val completedBooks: Int = 0,
    val currentlyReadingBooks: Int = 0,
    val wantToReadBooks: Int = 0,
    val pagesRead: Int = 0,

    val completedProgress: Float = 0f,
    val currentlyReadingProgress: Float = 0f,
    val wantToReadProgress: Float = 0f,

    val completedProgressPercent: Int = 0,
    val currentlyReadingProgressPercent: Int = 0,
    val wantToReadProgressPercent: Int = 0,

    val isLoading: Boolean = true,
    val hasError: Boolean = false,
)
