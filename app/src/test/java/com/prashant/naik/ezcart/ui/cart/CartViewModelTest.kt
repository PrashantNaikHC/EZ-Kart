package com.prashant.naik.ezcart.ui.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadOrdersUseCase
import com.prashant.naik.ezcart.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CartViewModelTest {

    lateinit var repository: FakeRepository
    private lateinit var viewModel: CartViewModel

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = FakeRepository()
        viewModel = CartViewModel(CartUseCase(repository), LoadOrdersUseCase(repository))
    }

    @Test
    fun `given user has items in cart, when on cart screen, should load the items in cart`() {
        val items = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.cartItems = items
        viewModel.getCartItems()
        val result = viewModel.cartItemsList.getOrAwaitValue()
        assertThat(items).isEqualTo(result)
    }

    @Test
    fun `given user has items in cart, when user removes an item, should load updated items`() {
        val initialItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        val finalItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        )
        repository.cartItems = initialItems
        viewModel.removeItemFromCart("item2")
        viewModel.getCartItems()
        val result = viewModel.cartItemsList.getOrAwaitValue()
        assertThat(finalItems).isEqualTo(result)
    }

    @Test
    fun `given user has items in cart, should get the total price of all items`() {
        val items = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.cartItems = items
        viewModel.getCartItems()
        val result = viewModel.getItemTotalPrice()
        assertThat(result).isEqualTo(300)
    }

    @Test
    fun `given user has items in cart, when places order, should clear all cart items`() {
        val items = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
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
        repository.cartItems = items
        repository.orders = ordersAdded
        viewModel.loadItemsToOrders(items, Order(
            listOf(
                Item("Dollar","desc1","12-12-21","item1",12,"1 pc"),
                Item("Dollar","desc1","12-12-21","item2",12,"1 pc"),
            ),
            "12-23-21",
            100,
            123,
            111
        ))
        viewModel.getCartItems()
        val result = viewModel.cartItemsList.getOrAwaitValue()
        assertThat(result).isEqualTo(listOf<Item>())
    }
}