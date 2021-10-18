package com.prashant.naik.ezcart.data.feedback

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
class FeedbackDaoTest {

    private lateinit var database: FeedbackDatabase
    private lateinit var dao: FeedbackDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FeedbackDatabase::class.java
        ).allowMainThreadQueries().build()
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