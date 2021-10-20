package com.prashant.naik.ezcart.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.AddToCartUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailsViewModelTest {

    private lateinit var viewModel : DetailsViewModel
    lateinit var repository: FakeRepository

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = FakeRepository()
        viewModel = DetailsViewModel(AddToCartUseCase(repository))
    }

    @Test
    fun `when user is on item details page, when adds the item to cart, should add item to cart database`() {
        val item = Item("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        viewModel.addToCart(item)
        assertThat(repository.cartItems).contains(item)
    }
}