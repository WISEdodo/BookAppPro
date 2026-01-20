package com.example.bookapppro.models

import java.io.Serializable

data class BookResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String?,
    val volumeInfo: VolumeInfo?
) : Serializable

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?
) : Serializable

data class ImageLinks(
    val thumbnail: String?
) : Serializable
