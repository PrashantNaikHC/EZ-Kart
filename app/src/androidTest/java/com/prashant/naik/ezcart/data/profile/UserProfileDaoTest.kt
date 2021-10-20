package com.prashant.naik.ezcart.data.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
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
class UserProfileDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: UserProfileDatabase
    private lateinit var dao: UserProfileDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
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