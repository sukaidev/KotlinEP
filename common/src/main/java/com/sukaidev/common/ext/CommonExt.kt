package com.sukaidev.common.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.common.rx.BaseFunc
import com.sukaidev.common.rx.BaseFuncBoolean
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.common.widget.DefaultTextWatcher
import com.trello.rxlifecycle.LifecycleProvider
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by sukai on 2019/08/10.
 * 扩展方法.
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.onClick(method: () -> Unit) {
    this.setOnClickListener {
        method()
    }
}

/**
 * 通过EditText状态修改Button状态
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}