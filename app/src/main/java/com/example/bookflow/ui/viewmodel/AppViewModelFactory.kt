package com.example.bookflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.bookflow.data.repository.BookRepository
import com.example.bookflow.data.repository.DiscoverRepository
import com.example.bookflow.ui.addbook.AddBookViewModel
import com.example.bookflow.ui.details.BookDetailsViewModel
import com.example.bookflow.ui.discover.DiscoverViewModel
import com.example.bookflow.ui.editbook.EditBookViewModel
import com.example.bookflow.ui.library.LibraryViewModel
import com.example.bookflow.ui.progress.ReadingProgressViewModel

class AppViewModelFactory(
    private val bookRepository: BookRepository,
    private val discoverRepository: DiscoverRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> {
                LibraryViewModel(bookRepository) as T
            }

            modelClass.isAssignableFrom(AddBookViewModel::class.java) -> {
                AddBookViewModel(bookRepository) as T
            }

            modelClass.isAssignableFrom(DiscoverViewModel::class.java) -> {
                DiscoverViewModel(
                    discoverRepository = discoverRepository,
                    bookRepository = bookRepository
                ) as T
            }

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(EditBookViewModel::class.java) -> {
                EditBookViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    bookRepository = bookRepository
                ) as T
            }

            modelClass.isAssignableFrom(BookDetailsViewModel::class.java) ->
                BookDetailsViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    bookRepository = bookRepository
                ) as T

            modelClass.isAssignableFrom(ReadingProgressViewModel::class.java) ->
                ReadingProgressViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    bookRepository = bookRepository
                ) as T

            else -> create(modelClass)
        }
    }
}
