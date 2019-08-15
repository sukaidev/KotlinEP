package com.sukaidev.user.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.user.presenter.view.IRegisterView
import com.sukaidev.user.service.IUserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class RegisterPresenter @Inject constructor() : BasePresenter<IRegisterView>() {

    @Inject
    lateinit var userService: IUserService

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