package com.prashant.naik.ezcart.ui.feedback

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.prashant.naik.ezcart.MainCoroutineRule
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.domain.FakeRepository
import com.prashant.naik.ezcart.domain.usecases.AddFeedbackUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FeedbackViewModelTest {

    lateinit var feedbackViewModel: FeedbackViewModel
    lateinit var repository: FakeRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        repository = FakeRepository()
        feedbackViewModel = FeedbackViewModel(AddFeedbackUseCase(repository))
    }

    @Test
    fun `given user provides a feedback, should be logged in database`() {
        val feedback = Feedback(123, "feedback", 4)
        feedbackViewModel.addFeedback(feedback)
        assertThat(repository.feedbacks).contains(feedback)
    }
}