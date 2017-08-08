package com.mo2o.template


import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class Future<T> {
    private val deferred: Deferred<T>

    private constructor(deferred: Deferred<T>) {
        this.deferred = deferred
    }

    constructor(f: () -> T) : this(async(CommonPool) { f() })

    fun <X> map(f: (T) -> X): Future<X> = Future(async(CommonPool) { f(deferred.await()) })

    fun <X> flatMap(f: (T) -> Future<X>): Future<X> = Future(async(CommonPool) { f(deferred.await()).deferred.await() })

    fun onComplete(f: (T) -> Unit) { launch(UI) { f(deferred.await()) } }
}