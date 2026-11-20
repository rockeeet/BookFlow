package com.example.bookflow.ui.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun StatisticsRoute(
    factory: ViewModelProvider.Factory,
    onAddBook: () -> Unit
) {
    val viewModel: StatisticsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    StatisticsScreen(
        uiState = uiState,
        onAddBook = onAddBook
    )
}