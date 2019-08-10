package com.sukaidev.common.presenter

import com.sukaidev.common.presenter.view.BaseView

/**
 * Created by sukai on 2019/08/10.
 *
 */
open class BasePresenter<T : BaseView> {
    lateinit var mView: T
}