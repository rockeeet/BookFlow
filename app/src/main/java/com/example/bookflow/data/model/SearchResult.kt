package com.example.bookflow.data.model

import java.io.Serializable

data class SearchResult(
    val id: String,
    val title: String,
    val author: String,
    val firstPublishYear: Int?,
    val coverUrl: String?
) : Serializable
