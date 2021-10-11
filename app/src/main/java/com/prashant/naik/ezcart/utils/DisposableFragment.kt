package com.prashant.naik.ezcart.utils

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 *  Fragment with a compositeDisposable object that gets cleared on onDestroyView()
 */
open class DisposableFragment : Fragment() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}