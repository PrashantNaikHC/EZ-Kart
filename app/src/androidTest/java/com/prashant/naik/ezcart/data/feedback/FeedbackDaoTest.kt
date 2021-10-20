package com.prashant.naik.ezcart.data.feedback

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
class FeedbackDaoTest {

    @Inject
    @Named("test_db")
    lateinit var database: FeedbackDatabase
    private lateinit var dao: FeedbackDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.feedbackDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun addFeedback() = runBlockingTest {
        val feedback = Feedback(123, "Very good",3)

        dao.insertFeedback(feedback)
        val results = dao.getAllFeedback()
        Truth.assertThat(results).isEqualTo(listOf(feedback))
    }

}