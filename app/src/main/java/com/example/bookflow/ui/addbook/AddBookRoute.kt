package com.example.bookflow.ui.addbook

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddBookRoute(
    factory: ViewModelProvider.Factory,
    onNavigateBack: () -> Unit,
    onBookSaved: () -> Unit
) {
    val viewModel: AddBookViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onCoverSelected(uri.toString())
        }
    }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onBookSaved()
        }
    }

    AddBookScreen(
        uiState = uiState,
        onTitleChanged = viewModel::onTitleChanged,
        onAuthorChanged = viewModel::onAuthorChanged,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onTotalPagesChanged = viewModel::onTotalPagesChanged,
        onPublicationYearChanged = viewModel::onPublicationYearChanged,
        onNavigateBack = onNavigateBack,
        onPickCover = {
            imagePicker.launch(arrayOf("image/*"))
        },
        onSaveBook = viewModel::saveBook
    )
}