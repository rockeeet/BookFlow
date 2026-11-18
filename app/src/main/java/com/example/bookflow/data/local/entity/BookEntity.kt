package com.example.bookflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val author: String,
    val coverUrl: String?,
    val publicationYear: Int?,
    val totalPages: Int?,
    val description: String?,
    val status: String,
    val currentPage: Int,

    val addedAt: Long,
    val externalId: String? = null
)