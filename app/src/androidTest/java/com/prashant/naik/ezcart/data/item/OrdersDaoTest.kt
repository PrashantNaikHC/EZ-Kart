package com.prashant.naik.ezcart.data.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
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
class OrdersDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: OrderDatabase
    private lateinit var dao: OrdersDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.orderItemsDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun addItemToCart() = runBlockingTest {
        val item11 = Item("currency11","desc11","12-12-2021","item11",100, "1 Kg")
        val item12 = Item("currency12","desc12","13-12-2021","item12",200, "2 Kg")
        val order1 = Order(listOf(item11, item12),"12-23-2021",234,123,251)

        val item21 = Item("currency21","desc21","12-12-2021","item21",100, "1 Kg")
        val item22 = Item("currency22","desc22","13-12-2021","item22",200, "2 Kg")
        val order2 = Order(listOf(item21, item22),"12-23-2021",234,123,252)

        dao.insertOrderItems(listOf(order1, order2))
        val resultItems = dao.getOrderItems()
        Truth.assertThat(resultItems).isEqualTo(listOf(order1, order2))
    }
}