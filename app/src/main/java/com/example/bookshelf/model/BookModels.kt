package com.example.bookshelf.model

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    val items: List<BookItem>? = emptyList()
)

data class BookItem(
    val id: String? = "",
    val volumeInfo: VolumeInfo? = VolumeInfo()
)

data class VolumeInfo(
    val title: String? = "Sin título",
    val authors: List<String>? = emptyList(),
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String? = null
) {
    val httpsThumbnail: String?
        get() = thumbnail?.replace("http://", "https://")
}
