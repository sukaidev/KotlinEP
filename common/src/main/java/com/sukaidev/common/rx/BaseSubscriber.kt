package com.sukaidev.common.rx

import rx.Subscriber

/**
 * Created by sukai on 2019/08/10.
 *
 */
open class BaseSubscriber<T> : Subscriber<T>() {
    override fun onNext(t: T) {
    }

    override fun onCompleted() {
    }

    override fun onError(e: Throwable?) {
    }

}