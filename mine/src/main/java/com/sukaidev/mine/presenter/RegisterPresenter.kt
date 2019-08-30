package com.sukaidev.mine.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.mine.presenter.view.RegisterView
import com.sukaidev.mine.service.UserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
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
//            mView.showLoading()
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