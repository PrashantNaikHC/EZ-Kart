package com.prashant.naik.ezcart.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import com.prashant.naik.ezcart.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LoginViewModelTest {

    lateinit var loginViewModel: LoginViewModel
    lateinit var repository : FakeRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var profile1 : UserProfile
    lateinit var profile2 : UserProfile

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = FakeRepository()
        loginViewModel = LoginViewModel(LoginUserUseCase(repository))
        profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
        profile2 = UserProfile(
            userId = "ravindra@gmail.com",
            firstName = "Ravindra",
            lastName = "Kumar",
            password = "QWERq1234!",
            phone = "8888888488"
        )
    }

    @Test
    fun `given registered user, when logged in, should login successfully`() = runBlockingTest {
        repository.registerUser(profile1)
        val result = loginViewModel.loginUser(profile1.userId, profile1.password).getOrAwaitValue()
        assertThat(result.first).isEqualTo(LoginState.SUCCESS)
        assertThat(profile1).isEqualTo(result.second)
    }

    @Test
    fun `given new user, when logged in, should return user not found login state`() = runBlockingTest {
        repository.registerUser(profile1)
        val result = loginViewModel.loginUser(profile2.userId, profile2.password).getOrAwaitValue()
        assertThat(result.first).isEqualTo(LoginState.USER_NOT_PRESENT)
        assertThat(result.second).isEqualTo(null)
    }

    @Test
    fun `given registered user, when wrong password is given, should return incorrect password login state`() = runBlockingTest {
        repository.registerUser(profile1)
        val result = loginViewModel.loginUser(profile1.userId, "asdf").getOrAwaitValue()
        assertThat(result.first).isEqualTo(LoginState.INCORRECT_PASSWORD)
        assertThat(result.second).isEqualTo(null)
    }

}