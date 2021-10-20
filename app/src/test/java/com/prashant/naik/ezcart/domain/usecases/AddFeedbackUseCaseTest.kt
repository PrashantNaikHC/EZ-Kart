package com.prashant.naik.ezcart.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.domain.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AddFeedbackUseCaseTest {

    lateinit var repository: FakeRepository
    private lateinit var useCase: AddFeedbackUseCase

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        useCase = AddFeedbackUseCase(repository)
    }

    @Test
    fun insertFeedbackUseCase() = runBlockingTest {
        val feedback = Feedback(123, "feedback", 4)
        useCase.insertFeedback(feedback)
        assertThat(repository.feedbacks).contains(feedback)
    }
}