package com.sukaidev.mine.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.mine.data.protocol.UserInfo

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
interface LoginView : BaseView {

    /**
     * 登录回调
     */
    fun onLoginResult(result: UserInfo)
}