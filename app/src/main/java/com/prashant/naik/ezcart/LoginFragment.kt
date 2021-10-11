package com.prashant.naik.ezcart

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.databinding.FragmentLoginBinding
import com.prashant.naik.ezcart.utils.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver

class LoginFragment : DisposableFragment() {

    private lateinit var binding : FragmentLoginBinding

    private var isUserNameValidated = false
    private var isPasswordValidated = false

    private lateinit var progressDialog: ProgressDialog

    // Uncomment to make the toolbar invisible
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
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
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }, Constants.LOGIN_DELAY)
        }

        val nameObservable = createTextInputLayoutObservable(binding.usernameInputEditText.editText!!)
        val passwordObservable = createTextInputLayoutObservable(binding.passwordInputEditText.editText!!)

        compositeDisposable.add(
            nameObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isUserNameValidated = binding.usernameInputEditText.validateInputIsEmail(text)
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
                        isPasswordValidated = binding.passwordInputEditText.validatePasswordIsValid(text)
                        updateLoginButton()
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(getString(R.string.login_dialog_text))
        progressDialog.setCancelable(false)
    }

    private fun updateLoginButton() {
        binding.loginButton.isEnabled = isLoginButtonEnabled()
    }

    private fun isLoginButtonEnabled() = isUserNameValidated && isPasswordValidated

}