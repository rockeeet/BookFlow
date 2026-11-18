package com.example.bookflow.ui.editbook

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
fun EditBookRoute(
    factory: ViewModelProvider.Factory,
    onNavigateBack: () -> Unit,
    onBookSaved: () -> Unit
) {
    val viewModel: EditBookViewModel = viewModel(
        factory = factory
    )

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

    EditBookScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onTitleChange = viewModel::onTitleChange,
        onAuthorChange = viewModel::onAuthorChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onTotalPagesChange = viewModel::onTotalPagesChange,
        onPublicationYearChange = viewModel::onPublicationYearChange,
        onStatusChange = viewModel::onStatusChange,
        onPickCover = {
            imagePicker.launch(arrayOf("image/*"))
        },
        onSaveChanges = viewModel::saveChanges
    )
}
