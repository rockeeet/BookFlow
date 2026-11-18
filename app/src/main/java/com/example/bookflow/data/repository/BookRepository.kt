package com.example.bookflow.data.repository

import com.example.bookflow.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<Book>>

    fun getBookById(id: Int): Flow<Book?>

    fun getBookByExternalId(externalId: String): Flow<Book?>

    suspend fun existsByExternalId(externalId: String): Boolean

    suspend fun insertBook(book: Book)

    suspend fun updateBook(book: Book)

    suspend fun deleteBook(book: Book)
}