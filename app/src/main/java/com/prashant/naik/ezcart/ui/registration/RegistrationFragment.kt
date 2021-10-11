package com.prashant.naik.ezcart.ui.registration

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.databinding.FragmentRegistrationBinding
import com.prashant.naik.ezcart.utils.*
import com.prashant.naik.ezcart.utils.Constants.Companion.LOGIN_DELAY
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : DisposableFragment() {

    lateinit var binding : FragmentRegistrationBinding

    private var isFirstNameValidated = false
    private var isLastNameValidated = false
    private var isUserNameValidated = false
    private var isPasswordValidated = false
    private var isMobileValidated = false

    private lateinit var progressDialog: ProgressDialog
    @Inject
    lateinit var factory: RegistrationViewModelFactory
    lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)
        viewModel = ViewModelProvider(this, factory).get(RegistrationViewModel::class.java)

        initProgressDialog()
        updateRegistrationButton()
        val firstNameObservable = createTextInputLayoutObservable(binding.firstNameInputEditText.editText!!)
        val lastNameObservable = createTextInputLayoutObservable(binding.lastNameInputEditText.editText!!)
        val userIdObservable = createTextInputLayoutObservable(binding.userIdInputEditText.editText!!)
        val passwordObservable = createTextInputLayoutObservable(binding.passwordInputEditText.editText!!)
        val mobileNumberObservable = createTextInputLayoutObservable(binding.mobileNumberInputEditText.editText!!)


        //region adding subscriptions
        compositeDisposable.add(
            firstNameObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isFirstNameValidated = binding.firstNameInputEditText.validateInputField(text)
                        updateRegistrationButton()
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )

        compositeDisposable.add(
            lastNameObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isLastNameValidated = binding.lastNameInputEditText.validateInputField(text)
                        updateRegistrationButton()
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )

        compositeDisposable.add(
            userIdObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isUserNameValidated = binding.userIdInputEditText.validateInputIsEmail(text)
                        updateRegistrationButton()
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
                        updateRegistrationButton()
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )
        compositeDisposable.add(
            mobileNumberObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>(), Observer<String> {
                    override fun onNext(text: String?) {
                        isMobileValidated = binding.mobileNumberInputEditText.validateInputIsMobile(text)
                        updateRegistrationButton()
                    }
                    override fun onError(e: Throwable?) {}
                    override fun onComplete() {}
                    override fun onSubscribe(d: io.reactivex.disposables.Disposable?) {}
                })
        )
        //endregion

        binding.registerButton.setOnClickListener {
            it.hideKeyboard()
            progressDialog.show()
            viewModel.registerNewUser(collectProfileData(binding))
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                progressDialog.dismiss()
                findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())
            }, LOGIN_DELAY)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun collectProfileData(binding: FragmentRegistrationBinding): UserProfile {
        return UserProfile(
            userId = binding.userIdInputEditText.editText?.text.toString(),
            firstName = binding.firstNameInputEditText.editText?.text.toString(),
            lastName = binding.lastNameInputEditText.editText?.text.toString(),
            password = binding.passwordInputEditText.editText?.text.toString(),
            phone = binding.mobileNumberInputEditText.editText?.text.toString()
        )
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage(getString(R.string.register_dialog_text))
        progressDialog.setCancelable(false)
    }

    private fun updateRegistrationButton() {
        binding.registerButton.isEnabled = isRegistrationButtonEnabled()
    }

    private fun isRegistrationButtonEnabled() =
        isFirstNameValidated && isLastNameValidated && isUserNameValidated && isPasswordValidated && isMobileValidated
}