package com.prashant.naik.ezcart.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.data.Order
import java.text.SimpleDateFormat
import java.util.*

fun TextInputLayout.validateInputIsEmail(target: String?): Boolean {
    var validationSuccessful = false
    if (Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
        this.isErrorEnabled = false
        this.helperText = context.getString(R.string.valid_email)
        validationSuccessful = true
    } else {
        this.error = context.getString(R.string.invalid_email)
    }
    return validationSuccessful
}

fun TextInputLayout.validateInputIsMobile(target: String?): Boolean {
    var validationSuccessful = false
    if (Patterns.PHONE.matcher(target).matches() && target?.length == 10) {
        this.isErrorEnabled = false
        this.helperText = context.getString(R.string.valid_mobile)
        validationSuccessful = true
    } else {
        this.error = context.getString(R.string.invalid_mobile)
    }
    return validationSuccessful
}

fun TextInputLayout.validateInputField(target: String?): Boolean {
    var validationSuccessful = false
    if(target != null) {
        if (target.isBlank() || target.isEmpty()) {
            this.error = context.getString(R.string.blank_input)
        } else {
            this.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun TextInputLayout.validateFeedbackField(target: String?): Boolean {
    var validationSuccessful = false
    if(target != null) {
        if (target.isBlank() || target.isEmpty()) {
            this.error = context.getString(R.string.blank_input)
        } else if (target.length > 400) {
            this.error = context.getString(R.string.feedback_text_limit_exceeded)
        }else {
            this.isErrorEnabled = false
            validationSuccessful = true
        }
    }
    return validationSuccessful
}

fun TextInputLayout.validateSignUpPassword(target: String?): Boolean {
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

fun TextInputLayout.validateSignInSuccess(isValidCredential: Boolean): Boolean {
    var isLoginSuccessful = false
    if(isValidCredential) {
        this.isErrorEnabled = false
        isLoginSuccessful = true
    }  else {
        this.error = context.getString(R.string.incorrect_password)
    }
    return isLoginSuccessful
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


@SuppressLint("SetTextI18n")
fun TextView.setOrderHeader(latestOrder: Order) {
    val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(latestOrder.orderDate)
    val month = SimpleDateFormat("MMMM", Locale.US).format(date!!)
    this.text = context.getString(R.string.orders_placeholder_pretext) + " " + month
}


