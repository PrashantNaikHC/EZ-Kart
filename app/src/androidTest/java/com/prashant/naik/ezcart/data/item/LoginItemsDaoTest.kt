package com.prashant.naik.ezcart.data.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginItemsDaoTest {

    private lateinit var database: LoginItemDatabase
    private lateinit var dao: LoginItemsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LoginItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.loginItemsDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertAndLoadLoginItems() = runBlockingTest {
        val loginItems = listOf(
            LoginItem("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        )

        dao.insertLoginItems(loginItems)
        val resultItems = dao.loadLoginItems()
        assertThat(resultItems).isEqualTo(loginItems)
    }

    @Test
    fun clearLoginItems() = runBlockingTest {
        val loginItems = listOf(
            LoginItem("currency1","desc1","12-12-2021","item1",100, "1 Kg")
        )

        dao.insertLoginItems(loginItems)
        dao.clearLoginItems()
        val resultItems = dao.loadLoginItems()
        assertThat(resultItems).isEqualTo(listOf<LoginItem>())
    }
}