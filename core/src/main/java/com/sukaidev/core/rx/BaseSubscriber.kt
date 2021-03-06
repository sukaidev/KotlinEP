package com.sukaidev.core.rx

import com.sukaidev.core.presenter.view.BaseView
import rx.Subscriber

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
open class BaseSubscriber<T>(private val baseView: BaseView) : Subscriber<T>() {
    override fun onNext(t: T) {
    }

    override fun onCompleted() {
        baseView.stopLoading()
    }

    override fun onError(e: Throwable) {
        baseView.stopLoading()
        if (e is BaseException) {
            baseView.onError(e.msg)
        }
    }

}