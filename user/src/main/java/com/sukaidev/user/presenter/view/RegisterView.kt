package com.sukaidev.user.presenter.view

import com.sukaidev.common.presenter.view.BaseView

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
interface RegisterView : BaseView {

    /**
     * 注册回调
     */
    fun onRegisterResult(result: String)
}