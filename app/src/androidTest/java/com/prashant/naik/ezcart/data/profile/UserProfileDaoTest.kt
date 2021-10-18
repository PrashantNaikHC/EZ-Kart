package com.prashant.naik.ezcart.data.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserProfileDaoTest {

    private lateinit var database: UserProfileDatabase
    private lateinit var dao: UserProfileDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserProfileDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.userProfileDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertUserProfile() = runBlockingTest {
        val profile = UserProfile("govind@gmail.com","govind","kumar","ASDFq1234!","7894561234")

        dao.addUserProfile(profile)
        val results = dao.getAllUserProfiles()
        Truth.assertThat(results).isEqualTo(listOf(profile))
    }

    @Test
    fun invalidUserIdReturnsNullProfile() = runBlockingTest {
        val profile = UserProfile("govind@gmail.com","govind","kumar","ASDFq1234!","7894561234")

        dao.addUserProfile(profile)
        val results = dao.loginUser("ramesh@gmail.com")
        Truth.assertThat(results).isEqualTo(null)
    }

    @Test
    fun validUserIdReturnsProfile() = runBlockingTest {
        val profile = UserProfile("govind@gmail.com","govind","kumar","ASDFq1234!","7894561234")

        dao.addUserProfile(profile)
        val results = dao.loginUser("govind@gmail.com")
        Truth.assertThat(results).isEqualTo(profile)
    }
}