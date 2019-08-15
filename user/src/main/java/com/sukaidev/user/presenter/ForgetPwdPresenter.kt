package com.sukaidev.user.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.user.presenter.view.IForgetPwdView
import com.sukaidev.user.service.IUserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
class ForgetPwdPresenter @Inject constructor() : BasePresenter<IForgetPwdView>() {

    @Inject
    lateinit var userService: IUserService

    fun forgetPwd(mobile: String, verifyCode: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.forgetPwd(mobile, verifyCode).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                if (t)
                    mView.onForgetPwdResult("验证成功")
            }
        }, lifecycleProvider)
    }
}