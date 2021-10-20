package com.prashant.naik.ezcart.ui.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.domain.usecases.AddFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(private val addFeedbackUseCase: AddFeedbackUseCase) : ViewModel() {

    fun addFeedback(feedback: Feedback) = viewModelScope.launch {
        addFeedbackUseCase.insertFeedback(feedback)
    }

}

@Suppress("UNCHECKED_CAST")
class FeedbackViewModelFactory @Inject constructor(private val addFeedbackUseCase: AddFeedbackUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(FeedbackViewModel::class.java) ->
                FeedbackViewModel(addFeedbackUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}