package com.sukaidev.mine.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.mine.presenter.view.ResetPwdView
import com.sukaidev.mine.service.UserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
class ResetPwdPresenter @Inject constructor() : BasePresenter<ResetPwdView>() {

    @Inject
    lateinit var userService: UserService

    fun resetPwd(mobile: String, pwd: String) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        userService.resetPwd(mobile, pwd).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                if (t)
                    mView.onResetPwdResult("重设密码成功")
            }
        }, lifecycleProvider)
    }
}