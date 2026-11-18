package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {

    @Test
    fun networkBooksRepository_getBooks_verifyBooksList() = runTest {
        val repository = NetworkBooksRepository(
            booksApiService = FakeBooksApiService()
        )
        val books = repository.getBooks("jazz history")
        assertEquals(2, books.size)
        assertEquals("1", books[0].id)
        assertEquals("http://example.com/1.jpg", books[0].volumeInfo?.imageLinks?.thumbnail)
    }
}
