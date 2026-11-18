package com.example.bookshelf

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BookSearchResponse
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.network.BooksApiService

class FakeBooksApiService : BooksApiService {
    override suspend fun searchBooks(query: String): BookSearchResponse {
        return BookSearchResponse(
            items = listOf(
                BookItem(id = "1", volumeInfo = VolumeInfo(title = "Jazz 1")),
                BookItem(id = "2", volumeInfo = VolumeInfo(title = "Jazz 2"))
            )
        )
    }

    override suspend fun getBook(id: String): BookItem {
        return BookItem(
            id = id,
            volumeInfo = VolumeInfo(
                title = "Jazz $id",
                authors = listOf("Author $id"),
                imageLinks = ImageLinks(thumbnail = "http://example.com/$id.jpg")
            )
        )
    }
}
