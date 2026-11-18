package com.example.bookshelf.data

import android.util.Log
import com.example.bookshelf.model.BookItem
import com.example.bookshelf.network.BooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String): List<BookItem>
}

class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): List<BookItem> {
        return try {
            val searchResponse = booksApiService.searchBooks(query)
            val items = searchResponse.items ?: emptyList()


            items.map { item ->
                val id = item.id
                if (item.volumeInfo?.imageLinks?.thumbnail == null && !id.isNullOrEmpty()) {
                    try {
                        booksApiService.getBook(id)
                    } catch (e: Exception) {
                        Log.w("NetworkBooksRepository", "Error fetching book detail for id: $id", e)
                        item
                    }
                } else {
                    item
                }
            }
        } catch (e: Exception) {
            Log.e("NetworkBooksRepository", "Error searching books for query: $query", e)
            throw e
        }
    }
}
