package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoadLoginItemsUseCaseTest {

    lateinit var repository: FakeRepository
    lateinit var useCase: LoadLoginItemsUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = LoadLoginItemsUseCase(repository)
    }
    
    @Test
    fun loadLoginItems() = runBlockingTest {
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        val result = useCase.loadLoginItems()
        Truth.assertThat(result).isEqualTo(loginItems)
    }

    @Test
    fun invalidateAndloadLoginItems() = runBlockingTest {
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        useCase.invalidateAndloadLoginItems()
        val result = useCase.loadLoginItems()
        Truth.assertThat(result).isEqualTo(emptyList<Item>())
    }
}