package com.sukaidev.core.rx

import com.sukaidev.common.rx.BaseException
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
//        baseView.hideLoading()
    }

    override fun onError(e: Throwable) {
//        baseView.hideLoading()
        if (e is BaseException) {
            baseView.onError(e.msg)
        }
    }

}