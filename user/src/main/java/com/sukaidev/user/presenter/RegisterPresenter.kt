package com.sukaidev.user.presenter

import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.user.presenter.view.RegisterView

/**
 * Created by sukai on 2019/08/10.
 *
 */
class RegisterPresenter : BasePresenter<RegisterView>() {

    fun register(mobile: String, verifyCode: String, pwd: String) {
        // 业务逻辑

        mView.onRegisterResult(true)

    }
}