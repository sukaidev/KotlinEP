package com.sukaidev.common.rx

import com.sukaidev.common.presenter.view.BaseView
import rx.Subscriber

/**
 * Created by sukai on 2019/08/10.
 *
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Subscriber<T>() {
    override fun onNext(t: T) {
    }

    override fun onCompleted() {
        baseView.hideLoading()
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        e.printStackTrace()
    }

}