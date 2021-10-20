package com.prashant.naik.ezcart.data.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.data.Item
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class CartItemsDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: CartItemsDatabase
    private lateinit var dao: CartItemsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.cartItemsDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun addItemToCart() = runBlockingTest {
        val item1 = Item("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        val item2 = Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")

        dao.addItemToCart(item1)
        dao.addItemToCart(item2)
        val resultItems = dao.getCartItems()
        assertThat(resultItems).isEqualTo(listOf(item1, item2))
    }

    @Test
    fun removeItemFromCart() = runBlockingTest {
        val item1 = Item("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        val item2 = Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")

        dao.addItemToCart(item1)
        dao.addItemToCart(item2)
        dao.removeItemFromCart(item1.itemName)
        val resultItems = dao.getCartItems()
        assertThat(resultItems).isEqualTo(listOf(item2))
    }

    @Test
    fun clearItemFromCart() = runBlockingTest {
        val item1 = Item("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        val item2 = Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")

        dao.addItemToCart(item1)
        dao.addItemToCart(item2)
        dao.clearCartItems()
        val resultItems = dao.getCartItems()
        assertThat(resultItems).isEqualTo(listOf<Item>())
    }

}