package com.prashant.naik.ezcart.utils

import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun createTextInputLayoutObservable(editText: EditText): Observable<String> =
    RxTextView.textChanges(editText)
        .skip(1)
        .debounce(1000, TimeUnit.MILLISECONDS)
        .map { it.toString() }