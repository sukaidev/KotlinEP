package com.sukaidev.user.presenter

import com.sukaidev.common.ext.execute
import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.user.data.protocol.UserInfo
import com.sukaidev.user.presenter.view.IUserInfoView
import com.sukaidev.user.service.IUploadService
import com.sukaidev.user.service.IUserService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/12.
 *
 */
class UserInfoPresenter @Inject constructor() : BasePresenter<IUserInfoView>() {

    @Inject
    lateinit var userService: IUserService

    @Inject
    lateinit var uploadService: IUploadService

    fun getUploadToken() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
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
        mView.showLoading()
        userService.editUser(userIcon, userName, userGender, userSign)
            .execute(object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onEditUserResult(t)
                }
            }, lifecycleProvider)
    }
}