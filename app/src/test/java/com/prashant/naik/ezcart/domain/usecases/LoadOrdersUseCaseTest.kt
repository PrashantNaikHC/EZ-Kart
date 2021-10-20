package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.domain.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoadOrdersUseCaseTest {

    lateinit var repository: FakeRepository
    private lateinit var useCase: LoadOrdersUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = LoadOrdersUseCase(repository)
    }
    
    @Test
    fun loadOrders() = runBlockingTest {
        val ordersAdded = mutableListOf(
            Order(
                listOf(
                    Item("Dollar","desc1","12-12-21","item1",12,"1 pc"),
                    Item("Dollar","desc1","12-12-21","item2",12,"1 pc"),
                ),
                "12-23-21",
                123,
                123,
                111
            ),
            Order(
                listOf(
                    Item("Dollar","desc1","12-12-21","item1",12,"1 pc"),
                    Item("Dollar","desc1","12-12-21","item2",12,"1 pc"),
                ),
                "12-23-21",
                124,
                124,
                222
            )
        )
        repository.orders = ordersAdded
        val result = useCase.loadOrders()
        Truth.assertThat(result).isEqualTo(ordersAdded)
    }
}