package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class AddFeedbackUseCase @Inject constructor(val repository: Repository){
    suspend fun insertFeedback(feedback: Feedback) = repository.addFeedback(feedback)
}