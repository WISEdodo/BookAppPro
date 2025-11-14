package models

import com.google.gson.annotations.SerializedName

/**
 * This is the top-level response from the API.
 * It contains a list of book items.
 */
data class BookResponse(
    @SerializedName("items")
    val items: List<BookItem>?
)

/**
 * This is the main "Book" object we will use everywhere.
 * We've added a custom "id" based on the tag for simplicity in the adapter.
 */
data class BookItem(
    @SerializedName("id")
    val id: String, // Use the API's 'id' field

    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?
)

/**
 * This class holds the main details about the book,
 * like title, description, and image links.
 */
data class VolumeInfo(
    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?
)

/**
 * This class holds the URLs for the book cover images.
 * The API provides different sizes. We'll use "thumbnail".
 */
data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String?
)