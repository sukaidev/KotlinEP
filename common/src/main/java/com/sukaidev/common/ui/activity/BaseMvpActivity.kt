package com.sukaidev.common.ui.activity

import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.presenter.view.BaseView

/**
 * Created by sukai on 2019/08/10.
 *
 */
open class BaseMvpActivity<T : BasePresenter<*>> : BaseActivity(), BaseView {

    lateinit var mPresenter: T

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError() {
    }
}