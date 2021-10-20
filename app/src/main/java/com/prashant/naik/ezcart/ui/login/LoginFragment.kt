package com.prashant.naik.ezcart.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.databinding.FragmentLoginBinding
import com.prashant.naik.ezcart.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : DisposableFragment() {

    private lateinit var binding: FragmentLoginBinding

    private var isUserNameValidated = false

    @Inject
    lateinit var factory: LoginViewModelFactory
    lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressDialog

    // Uncomment to make the toolbar invisible
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }

        initProgressDialog()
        updateLoginButton()
        binding.loginButton.setOnClickListener {
            it.hideKeyboard()
            progressDialog.show()
            Handler(Looper.getMainLooper()).postDelayed({
                progressDialog.dismiss()
                viewModel.loginUser(
                    binding.usernameInputEditText.editText?.text.toString(),
                    binding.passwordInputEditText.editText?.text.toString().toSHA256()
                ).observe(viewLifecycleOwner, { userProfilePair ->
                    if (validateSignInSuccess(
                            binding.passwordInputEditText,
                            userProfilePair.first
                        )
                    ) {
                        userProfilePair.second?.let { userProfile ->
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(userProfile))
                        }
                    }
                })
            }, Constants.LOGIN_DELAY)
        }

        val nameObservable =
            createTextInputLayoutObservable(binding.usernameInputEditText.editText!!)
        val passwordObservable =
            createTextInputLayoutObservable(binding.passwordInputEditText.editText!!)

        binding.signUpTextView.text = setUnderlinedString()

        //region adding subscriptions
        compositeDisposable.add(
            nameObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String) {
                        isUserNameValidated =
                            validateInputIsEmail(binding.usernameInputEditText, text)
                        updateLoginButton()
                    }

                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )

        compositeDisposable.add(
            passwordObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        binding.passwordInputEditText.isErrorEnabled = false
                        updateLoginButton()
                    }

                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )
        //endregion

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUnderlinedString(): SpannableString {
        val spannable = SpannableString(getString(R.string.signup))
        spannable.setSpan(
            UnderlineSpan(),
            0, // start
            getString(R.string.signup).length, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannable
    }

    @Suppress("DEPRECATION")
    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(getString(R.string.login_dialog_text))
        progressDialog.setCancelable(false)
    }

    private fun updateLoginButton() {
        binding.loginButton.isEnabled = isUserNameValidated
    }

}