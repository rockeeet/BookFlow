package com.example.bookflow.data.repository

import com.example.bookflow.data.local.dao.BookDao
import com.example.bookflow.data.mapper.toBook
import com.example.bookflow.data.mapper.toEntity
import com.example.bookflow.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val bookDao: BookDao
): BookRepository {
    override fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
            .map { books ->
                books.map { it.toBook() }
            }
    }

    override fun getBookById(id: Int): Flow<Book?> {
        return bookDao.getBookById(id)
            .map { it?.toBook() }
    }

    override fun getBookByExternalId(externalId: String): Flow<Book?> {
        return bookDao.getBookByExternalId(externalId)
            .map { it?.toBook() }
    }

    override suspend fun existsByExternalId(externalId: String): Boolean {
        return bookDao.existsByExternalId(externalId)
    }

    override suspend fun insertBook(book: Book) {
        bookDao.insertBook(
            book.toEntity(
                addedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun updateBook(book: Book) {
        val existingBook = bookDao.getBookByIdOnce(book.id)
            ?: return
        bookDao.updateBook(
            book.toEntity(
                addedAt = existingBook.addedAt
            )
        )
    }

    override suspend fun deleteBook(book: Book) {
        val existingBook = bookDao.getBookByIdOnce(book.id)
            ?: return

        bookDao.deleteBook(existingBook)
    }
}