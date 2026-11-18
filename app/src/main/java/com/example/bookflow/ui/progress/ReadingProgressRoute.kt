package com.example.bookflow.ui.progress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReadingProgressRoute(
    factory: ViewModelProvider.Factory,
    onNavigateBack: () -> Unit
) {
    val viewModel: ReadingProgressViewModel = viewModel(
        factory = factory
    )

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    ReadingProgressScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onCurrentPageChange = viewModel::onCurrentPageChange,
        onIncrementPage = viewModel::incrementPage,
        onDecrementPage = viewModel::decrementPage,
        onSaveProgress = viewModel::saveProgress,
        onMarkCompleted = viewModel::markAsCompleted
    )
}