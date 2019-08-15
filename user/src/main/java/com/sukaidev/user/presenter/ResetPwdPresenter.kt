package com.sukaidev.user.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.user.presenter.view.IResetPwdView
import com.sukaidev.user.service.IUserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
class ResetPwdPresenter @Inject constructor() : BasePresenter<IResetPwdView>() {

    @Inject
    lateinit var userService: IUserService

    fun resetPwd(mobile: String, pwd: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userService.resetPwd(mobile, pwd).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                if (t)
                    mView.onResetPwdResult("重设密码成功")
            }
        }, lifecycleProvider)
    }
}