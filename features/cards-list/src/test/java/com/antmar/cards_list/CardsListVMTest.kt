package com.antmar.cards_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.antmar.cards_list.domain.CardsListRepository
import com.antmar.cards_list.domain.usecases.DeleteCardUseCase
import com.antmar.cards_list.domain.usecases.GetAllCardsUseCase
import com.antmar.cards_list.domain.usecases.SendIdUseCase
import com.antmar.cards_list.presentation.viewmodels.CardsListViewModel
import com.antmar.core.domain.entity.CardUIEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class CardsListVMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val getAllCardsUseCaseMock = mockk<GetAllCardsUseCase>()
    private val deleteCardUseCaseMock = mockk<DeleteCardUseCase>()
    private val sendIdUseCaseMock = mockk<SendIdUseCase>()

    private lateinit var viewModel: CardsListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun initShouldCollectCards() = runTest {
        val testCards = listOf(
            CardUIEntity(id = 1, name = "test1", code = "code", color = 100L, isBarcode = false),
            CardUIEntity(id = 2, name = "test2", code = "code", color = 101L, isBarcode = false),
        )

        coEvery { getAllCardsUseCaseMock.invoke() } returns flowOf(testCards)

        viewModel =
            CardsListViewModel(getAllCardsUseCaseMock,
                deleteCardUseCaseMock,
                sendIdUseCaseMock)

        viewModel.allCardsListState.test {
            assertEquals(emptyList<CardUIEntity>(), awaitItem())

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(testCards, awaitItem())
            cancel()
        }

        coVerify { getAllCardsUseCaseMock.invoke() }
    }
}