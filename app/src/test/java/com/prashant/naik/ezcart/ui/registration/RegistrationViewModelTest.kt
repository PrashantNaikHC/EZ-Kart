package com.prashant.naik.ezcart.ui.registration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.RegisterUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RegistrationViewModelTest {

    lateinit var registrationViewModel: RegistrationViewModel
    lateinit var repository : FakeRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var profile1 : UserProfile

    @Before
    fun setup() {
        repository = FakeRepository()
        registrationViewModel = RegistrationViewModel(RegisterUserUseCase(repository))
        profile1 = UserProfile(
            userId = "govinda@gmail.com",
            firstName = "Govinda",
            lastName = "Kumar",
            password = "ASDFq1234!",
            phone = "8888888888"
        )
    }

    @Test
    fun `given new user, when registered, should show in user profiles`() = runBlockingTest {
        repository.registerUser(profile1)
        registrationViewModel.registerNewUser(profile1)
        Truth.assertThat(repository.userProfiles).contains(profile1)
    }

}