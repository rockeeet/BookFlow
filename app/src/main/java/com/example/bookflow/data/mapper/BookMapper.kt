package com.example.bookflow.data.mapper

import com.example.bookflow.data.local.entity.BookEntity
import com.example.bookflow.data.model.Book
import com.example.bookflow.data.model.ReadingStatus

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        coverUrl = coverUrl,
        publicationYear = publicationYear,
        totalPages = totalPages,
        description = description,
        status = ReadingStatus.valueOf(status),
        currentPage = currentPage,
        externalId = externalId
    )
}

fun Book.toEntity(addedAt: Long): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        author = author,
        coverUrl = coverUrl,
        publicationYear = publicationYear,
        totalPages = totalPages,
        description = description,
        status = status.name,
        currentPage = currentPage,
        addedAt = addedAt,
        externalId = externalId
    )
}