package com.sukaidev.user.presenter

import android.widget.Toast
import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.common.utils.NetWorkUtils
import com.sukaidev.user.presenter.view.RegisterView
import com.sukaidev.user.service.UserService
import com.sukaidev.user.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * Created by sukai on 2019/08/10.
 *
 */
class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserService

    fun register(mobile: String, pwd: String, verifyCode: String) {
        // 业务逻辑
        if (!checkNetWork()) {
            return
        } else {
            mView.showLoading()
            userService.register(mobile, pwd, verifyCode)
                    .execute(object : BaseSubscriber<Boolean>(mView) {
                        override fun onNext(t: Boolean) {
                            if (t)
                                mView.onRegisterResult("注册成功")
                        }
                    }, lifecycleProvider)
        }
    }
}