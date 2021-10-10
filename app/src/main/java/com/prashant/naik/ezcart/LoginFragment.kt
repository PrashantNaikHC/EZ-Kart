package com.prashant.naik.ezcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import com.prashant.naik.ezcart.databinding.FragmentLoginBinding
import com.prashant.naik.ezcart.utils.hideKeyboard
import com.prashant.naik.ezcart.utils.validateInputIsEmail
import com.prashant.naik.ezcart.utils.validatePasswordIsValid
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val compositeDisposable = CompositeDisposable()

    private var isUserNameValidated = false
    private var isPasswordValidated = false

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

        binding.loginButton.isEnabled = isUserNameValidated && isPasswordValidated
        binding.loginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            it.hideKeyboard()
        }

        val nameObservable = RxTextView.textChanges(binding.usernameInputEditText.editText!!)
            .skip(1)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .map { it.toString() }

        val passwordObservable = RxTextView.textChanges(binding.passwordInputEditText.editText!!)
            .skip(1)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .map { it.toString() }

        compositeDisposable.add(
            nameObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isUserNameValidated = binding.usernameInputEditText.validateInputIsEmail(text)
                        binding.loginButton.isEnabled = isUserNameValidated && isPasswordValidated
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
                        binding.loginButton.isEnabled = isPasswordValidated && isUserNameValidated
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}