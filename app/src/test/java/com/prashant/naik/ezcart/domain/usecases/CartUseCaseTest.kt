package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CartUseCaseTest {

    lateinit var repository: FakeRepository
    lateinit var useCase: CartUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = CartUseCase(repository)
    }

    @Test
    fun getCartItems() = runBlockingTest {
        val items = mutableListOf(
            Item("currency1", "desc1", "12-12-2021", "item1", 100, "1 Kg"),
            Item("currency2", "desc2", "13-12-2021", "item2", 200, "2 Kg")
        )
        repository.cartItems = items
        val result = useCase.getCartItems()
        Truth.assertThat(result).isEqualTo(items)
    }

    @Test
    fun removeCartItem() = runBlockingTest {
        val item1 = Item("currency1", "desc1", "12-12-2021", "item1", 100, "1 Kg")
        val item2 = Item("currency2", "desc2", "13-12-2021", "item2", 200, "2 Kg")
        val items = mutableListOf(
            item1,
            item2
        )
        repository.cartItems = items
        useCase.removeCartItem(item1.itemName)
        Truth.assertThat(repository.cartItems).doesNotContain(item1)
    }
}