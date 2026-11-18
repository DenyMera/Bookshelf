package com.example.bookshelf

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class FakeBooksRepository : BooksRepository {
    val fakeBooks = listOf(
        BookItem("1", VolumeInfo("The History of Jazz", listOf("Ted Gioia"), ImageLinks("http://example.com/1.jpg"))),
        BookItem("2", VolumeInfo("Jazz Styles", listOf("Mark C. Gridley"), ImageLinks("http://example.com/2.jpg")))
    )

    override suspend fun getBooks(query: String): List<BookItem> = fakeBooks
}

class BookshelfViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun bookshelfViewModel_getBooks_verifyBookshelfUiStateSuccess() = runTest {
        val fakeRepository = FakeBooksRepository()
        val viewModel = BookshelfViewModel(booksRepository = fakeRepository)

        advanceUntilIdle()

        assertEquals(
            BookshelfUiState.Success(fakeRepository.fakeBooks),
            viewModel.bookshelfUiState
        )
    }
}
