package com.sukaidev.mine.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.mine.data.protocol.UserInfo
import com.sukaidev.mine.presenter.view.UserInfoView
import com.sukaidev.mine.service.UploadService
import com.sukaidev.mine.service.UserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var uploadService: UploadService

    fun getUploadToken() {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetUploadTokenResult(t)
            }
        }, lifecycleProvider)
    }

    fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        userService.editUser(userIcon, userName, userGender, userSign)
            .execute(object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onEditUserResult(t)
                }
            }, lifecycleProvider)
    }
}