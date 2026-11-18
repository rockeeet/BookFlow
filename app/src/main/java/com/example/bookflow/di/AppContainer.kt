package com.example.bookflow.di

import android.content.Context
import com.example.bookflow.data.local.BookDatabase
import com.example.bookflow.ui.viewmodel.AppViewModelFactory
import com.example.bookflow.data.repository.BookRepository
import com.example.bookflow.data.repository.BookRepositoryImpl
import com.example.bookflow.data.remote.OpenLibraryApiClient
import com.example.bookflow.data.repository.DiscoverRepository
import com.example.bookflow.data.repository.DiscoverRepositoryImpl

class AppContainer(context: Context) {

    private val bookDatabase = BookDatabase.getDatabase(context)

    private val bookRepository: BookRepository by lazy {
        BookRepositoryImpl(bookDatabase.bookDao())
    }

    private val discoverRepository: DiscoverRepository by lazy {
        DiscoverRepositoryImpl(OpenLibraryApiClient.api)
    }

    val appViewModelFactory: AppViewModelFactory by lazy {
        AppViewModelFactory(
            bookRepository = bookRepository,
            discoverRepository = discoverRepository
        )
    }
}