package com.prashant.naik.ezcart.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.databinding.FragmentFeedbackBinding
import com.prashant.naik.ezcart.utils.DisposableFragment
import com.prashant.naik.ezcart.utils.createTextInputLayoutObservable
import com.prashant.naik.ezcart.utils.hideKeyboard
import com.prashant.naik.ezcart.utils.validateFeedbackField
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

@AndroidEntryPoint
class FeedbackFragment : DisposableFragment() {

    lateinit var binding : FragmentFeedbackBinding
    @Inject
    lateinit var factory: FeedbackViewModelFactory
    lateinit var viewModel: FeedbackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false)
        viewModel = ViewModelProvider(this, factory).get(FeedbackViewModel::class.java)

        val feedbackObservable = createTextInputLayoutObservable(binding.editTextTextMultiLine.editText!!)
        compositeDisposable.add(
            feedbackObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        updateSubmitButtonState(binding.editTextTextMultiLine.validateFeedbackField(text))
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )


        binding.submitFeedbackButton.setOnClickListener {
            if(binding.ratingBar.rating.toInt() == 0) {
                Toast.makeText(requireActivity(), getString(R.string.feedback_update), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addFeedback(Feedback(
                id = 0,
                feedbackText = binding.editTextTextMultiLine.editText!!.text.toString(),
                rating = binding.ratingBar.rating.toInt()
            ))
            Toast.makeText(requireActivity(), getString(R.string.feedback_success), Toast.LENGTH_SHORT).show()
            binding.root.hideKeyboard()
            findNavController().popBackStack()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateSubmitButtonState(validateFeedbackField: Boolean) {
        binding.submitFeedbackButton.isEnabled = validateFeedbackField
    }
}