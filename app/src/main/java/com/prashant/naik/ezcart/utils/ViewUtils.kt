package com.prashant.naik.ezcart.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.data.Order
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

fun TextInputLayout.setupInputLayout(
    errorString: String? = null,
    helperString: String? = null,
) {
    errorString?.let {
        this.isErrorEnabled = true
        this.error = it
    }
    helperString?.let {
        this.isErrorEnabled = false
        helperText = it
    }
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

fun loadProfilePictureFromInternalStorage(context: Context, userId: String): Bitmap? {
    val cw = ContextWrapper(context)
    val directory = cw.getDir(Constants.IMAGE_DIRECTORY, Context.MODE_PRIVATE)
    var bitmap: Bitmap? = null
    try {
        val f = File(directory, "$userId.jpg")
        bitmap = BitmapFactory.decodeStream(FileInputStream(f))
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    return bitmap
}