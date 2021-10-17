package com.prashant.naik.ezcart.utils

import androidx.core.util.PatternsCompat
import com.google.android.material.textfield.TextInputLayout
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.ui.login.LoginState
import com.prashant.naik.ezcart.utils.Constants.Companion.FEEDBACK_TEXT_LIMIT


fun validateInputIsEmail(textInputLayout: TextInputLayout?, target: String): Boolean {
    var validationSuccessful = false
    if (target.isEmpty() || PatternsCompat.EMAIL_ADDRESS.matcher(target).matches()) {
        textInputLayout?.setupInputLayout(
            helperString = textInputLayout.context?.getString(R.string.valid_email)
        )
        validationSuccessful = true
    } else {
        if(textInputLayout != null) {
            textInputLayout.setupInputLayout(
                errorString = textInputLayout.context?.getString(R.string.invalid_email)
            )
        }
    }
    return validationSuccessful
}

fun validateInputIsMobile(textInputLayout: TextInputLayout?, target: String?): Boolean {
    var validationSuccessful = false
    if (target?.hasNoDigits() == false && target.length == 10) {
        textInputLayout?.setupInputLayout(
            helperString = textInputLayout.context.getString(R.string.valid_mobile)
        )
        validationSuccessful = true
    } else {
        textInputLayout?.setupInputLayout(
            errorString = textInputLayout.context.getString(R.string.invalid_mobile)
        )
    }
    return validationSuccessful
}

private fun String.hasNoDigits(): Boolean {
    return this.any { !it.isDigit() }
}

fun validateInputField(textInputLayout: TextInputLayout?, target: String?): Boolean {
    var validationSuccessful = false
    if (target != null) {
        if (target.isBlank() || target.isEmpty()) {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.blank_input)
            )
        } else {
            textInputLayout?.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun validateFeedbackField(textInputLayout: TextInputLayout?, target: String?): Boolean {
    var validationSuccessful = false
    if (target != null) {
        if (target.isBlank() || target.isEmpty()) {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.blank_input)
            )
        } else if (target.length > FEEDBACK_TEXT_LIMIT) {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.feedback_text_limit_exceeded)
            )
        } else {
            textInputLayout?.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun validateSignUpPassword(textInputLayout: TextInputLayout?, target: String?): Boolean {
    var validationSuccessful = false
    val numberRegex = Regex("[0-9]+")
    val lowerLettersRegex = Regex("[a-z]+")
    val upperLettersRegex = Regex("[A-Z]+")
    val specialCharsRegex = Regex("[:;~!@#$%^&*()_+\\-=]")
    when {
        target!!.length < 8 -> textInputLayout?.error = textInputLayout?.context?.getString(R.string.password_validation_text_1)
        !target.contains(numberRegex) -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.password_validation_text_2)
            )
        }
        !target.contains(lowerLettersRegex) -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.password_validation_text_3)
            )
        }
        !target.contains(upperLettersRegex) -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.password_validation_text_4)
            )
        }
        !target.contains(specialCharsRegex) -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.password_validation_text_5)
            )
        }
        else -> {
            textInputLayout?.helperText = textInputLayout?.context?.getString(R.string.valid_password)
            textInputLayout?.isErrorEnabled = false
            textInputLayout?.setupInputLayout(
                helperString = textInputLayout.context.getString(R.string.valid_password)
            )
            textInputLayout?.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun validateSignInSuccess(
    textInputLayout: TextInputLayout?,
    loginState: LoginState
): Boolean {
    var isLoginSuccessful = false
    when (loginState) {
        LoginState.SUCCESS -> {
            textInputLayout?.isErrorEnabled = false
            isLoginSuccessful = true
        }
        LoginState.INCORRECT_PASSWORD -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.incorrect_password)
            )
        }
        LoginState.USER_NOT_PRESENT -> {
            textInputLayout?.setupInputLayout(
                errorString = textInputLayout.context.getString(R.string.user_not_found)
            )
        }
    }
    return isLoginSuccessful
}
