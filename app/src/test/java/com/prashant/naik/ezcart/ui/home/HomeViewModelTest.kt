package com.prashant.naik.ezcart.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadLoginItemsUseCase
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import com.prashant.naik.ezcart.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var repository: FakeRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = FakeRepository()
        homeViewModel = HomeViewModel(
            LoadLoginItemsUseCase(repository),
            CartUseCase(repository),
            LoginUserUseCase(repository)
        )
    }

    @Test
    fun `when user lands on home screen, load the items`() {
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        homeViewModel.loadLoginItems()
        val result = homeViewModel.loginItems.getOrAwaitValue()
        assertThat(loginItems).isEqualTo(result)
    }

    @Test
    fun `given user is in home screen, when user has items added in cart, load the items`() {
        val itemsAddedToCart = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.cartItems = itemsAddedToCart
        val result = homeViewModel.getItemsOnCart().getOrAwaitValue()
        assertThat(itemsAddedToCart.size).isEqualTo(result)
    }

    @Test
    fun `given user is in home screen, when user logs out, items should be cleared off the database`() {
        val itemsAddedToCart = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.cartItems = itemsAddedToCart
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        homeViewModel.clearUserData()
        assertThat(repository.cartItems).isEmpty()
        assertThat(repository.loginItems).isEmpty()
    }

    @Test
    fun `given user is in home screen, when user pulls to refresh the list, items should be cleared off the database`() {
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        homeViewModel.loadLoginItems()
        homeViewModel.invalidateAndLoadLoginItems()
        val result = homeViewModel.loginItems.getOrAwaitValue()
        assertThat(result).isEqualTo(emptyList<Item>())
    }
}