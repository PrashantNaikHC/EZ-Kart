package com.prashant.naik.ezcart.utils

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputLayout
import com.prashant.naik.ezcart.R

fun TextInputLayout.validateInputIsEmail(target: String?): Boolean {
    var validationSuccessful = false
    if (Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
        this.isErrorEnabled = false
        this.helperText = context.getString(R.string.valid_email)
        validationSuccessful = true
    } else {
        this.error = context.getString(R.string.imvalid_email)
    }
    return validationSuccessful
}

fun TextInputLayout.validatePasswordIsValid(target: String?): Boolean {
    var validationSuccessful = false
    val numberRegex = Regex("[0-9]+")
    val lowerLettersRegex = Regex("[a-z]+")
    val upperLettersRegex = Regex("[A-Z]+")
    val specialCharsRegex = Regex("[:;~!@#$%^&*()_+\\-=]")
    when {
        target!!.length < 8 ->  this.error = context.getString(R.string.password_validation_text_1)
        !target.contains(numberRegex) -> this.error = context.getString(R.string.password_validation_text_2)
        !target.contains(lowerLettersRegex) -> this.error = context.getString(R.string.password_validation_text_3)
        !target.contains(upperLettersRegex) -> this.error = context.getString(R.string.password_validation_text_4)
        !target.contains(specialCharsRegex) -> this.error = context.getString(R.string.password_validation_text_5)
        else -> {
            this.helperText = context.getString(R.string.valid_password)
            this.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

