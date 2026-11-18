package com.example.bookflow.data.remote

import com.google.gson.annotations.SerializedName

data class OpenLibrarySearchResponseDto(
    @SerializedName("docs")
    val books: List<OpenLibraryBookDto>?
)

data class OpenLibraryBookDto(
    @SerializedName("key")
    val key: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("author_name")
    val authors: List<String>?,

    @SerializedName("first_publish_year")
    val firstPublishYear: Int?,

    @SerializedName("cover_i")
    val coverId: Int?
)