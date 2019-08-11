package com.sukaidev.common.presenter

import android.content.Context
import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.common.utils.NetWorkUtils
import com.trello.rxlifecycle.LifecycleProvider
import javax.inject.Inject

/**
 * Created by sukai on 2019/08/10.
 *
 */
open class BasePresenter<T : BaseView> {

    lateinit var mView: T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>

    @Inject
    lateinit var context: Context

    fun checkNetWork(): Boolean {
        return NetWorkUtils.isNetWorkAvailable(context)
    }
}