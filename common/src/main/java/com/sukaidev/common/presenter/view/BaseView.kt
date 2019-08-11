package com.sukaidev.common.presenter.view

/**
 * Created by sukai on 2019/08/10.
 *
 */
interface BaseView {
    /**
     * 显示加载框
     */
    fun showLoading()
    /**
     * 隐藏加载框
     */
    fun hideLoading()

    fun onError()
}