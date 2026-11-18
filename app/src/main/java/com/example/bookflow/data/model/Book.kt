package com.example.bookflow.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val coverUrl: String?,
    val publicationYear: Int?,
    val totalPages: Int?,
    val description: String?,
    val status: ReadingStatus,
    val currentPage: Int,
    val externalId: String? = null
) {
    val progress: Float
        get() = totalPages
            ?.takeIf { it > 0 }
            ?.let {
                (currentPage.toFloat() / it).coerceIn(0f, 1f)
            }
            ?: 0f
}
