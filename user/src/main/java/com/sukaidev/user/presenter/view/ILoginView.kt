package com.sukaidev.user.presenter.view

import com.sukaidev.common.presenter.view.BaseView
import com.sukaidev.user.data.protocol.UserInfo

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
interface ILoginView : BaseView {

    /**
     * 登录回调
     */
    fun onLoginResult(result: UserInfo)
}