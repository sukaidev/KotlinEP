package com.sukaidev.core.presenter.view

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
interface BaseView {
    /**
     * 显示加载框
     */
//    fun showLoading()

    /**
     * 隐藏加载框
     */
//    fun hideLoading()

    fun onError(msg: String)
}