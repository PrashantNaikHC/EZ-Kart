package com.prashant.naik.ezcart.ui.orders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.LoadOrdersUseCase
import com.prashant.naik.ezcart.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class OrderViewModelTest {

    private lateinit var ordersViewModel: OrdersViewModel
    lateinit var repository : FakeRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var profile1 : UserProfile

    @Before
    fun setup() {
        repository = FakeRepository()
        ordersViewModel = OrdersViewModel(LoadOrdersUseCase(repository))
        profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
    }

    @Test
    fun `given registered user, when orders are present in repository, should load orders`() = runBlockingTest {
        repository.registerUser(profile1)
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
        val result = ordersViewModel.loadOrders().getOrAwaitValue()
        Truth.assertThat(result).isEqualTo(ordersAdded)
    }

}