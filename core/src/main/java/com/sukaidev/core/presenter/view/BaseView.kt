package com.sukaidev.core.presenter.view

import com.sukaidev.core.ui.activities.ProxyActivity
import com.sukaidev.core.ui.loader.Loader

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
interface BaseView {
    /**
     * 显示加载框
     */
    fun showLoading(){
        Loader.showLoading(ProxyActivity.instance)
    }

    /**
     * 停止加载
     */
    fun stopLoading(){
        Loader.stopLoading()
    }

    fun onError(msg: String)
}