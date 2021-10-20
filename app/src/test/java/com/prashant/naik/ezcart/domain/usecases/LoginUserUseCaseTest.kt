package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginUserUseCaseTest {

    lateinit var repository: FakeRepository
    private lateinit var useCase: LoginUserUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = LoginUserUseCase(repository)
    }

    @Test
    fun loginUser() = runBlockingTest {
        val profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
        repository.userProfiles = mutableListOf(profile1)
        val resultProfile = useCase.loginUser(profile1.userId)
        Truth.assertThat(resultProfile).isEqualTo(profile1)
    }

    @Test
    fun loginUserPresent() = runBlockingTest {
        val profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
        repository.userProfiles = mutableListOf(profile1)
        val resultProfile = useCase.loginUser(profile1.userId)
        Truth.assertThat(resultProfile).isEqualTo(profile1)
    }

    @Test
    fun loginUserNotPresent() = runBlockingTest {
        val profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
        repository.userProfiles = mutableListOf(profile1)
        val resultProfile = useCase.loginUser("asdf")
        Truth.assertThat(resultProfile).isEqualTo(null)
    }

    @Test
    fun logOutUser() = runBlockingTest {
        val loginItems = mutableListOf(
            Item("currency1","desc1","12-12-2021","item1",100, "1 Kg"),
            Item("currency2","desc2","13-12-2021","item2",200, "2 Kg")
        )
        repository.loginItems = loginItems
        val item1 = Item("currency1", "desc1", "12-12-2021", "item1", 100, "1 Kg")
        val item2 = Item("currency2", "desc2", "13-12-2021", "item2", 200, "2 Kg")
        val cartItems = mutableListOf(
            item1,
            item2
        )
        repository.cartItems = cartItems
        useCase.logOutUser()
        Truth.assertThat(repository.cartItems).isEmpty()
        Truth.assertThat(repository.loginItems).isEmpty()
    }
}