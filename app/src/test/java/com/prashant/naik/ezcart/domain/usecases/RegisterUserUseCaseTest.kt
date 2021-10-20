package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.FakeRepository
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
class RegisterUserUseCaseTest {

    lateinit var repository: FakeRepository
    lateinit var useCase: RegisterUserUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = RegisterUserUseCase(repository)
    }
    
    @Test
    fun registerUser() = runBlockingTest {
        val profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
        useCase.registerUser(profile1)
        Truth.assertThat(repository.userProfiles).contains(profile1)
    }
}