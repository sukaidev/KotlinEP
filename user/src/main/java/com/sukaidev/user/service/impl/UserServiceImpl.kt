package com.sukaidev.user.service.impl

import com.sukaidev.common.ext.convert
import com.sukaidev.common.ext.convertBoolean
import com.sukaidev.user.data.protocol.UserInfo
import com.sukaidev.user.data.repository.UserRepository
import com.sukaidev.user.service.IUserService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class UserServiceImpl @Inject constructor() : IUserService {

    @Inject
    lateinit var repository: UserRepository

    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, pwd, pushId)
            .convert()
    }

    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        return repository.register(mobile, pwd, verifyCode)
            .convertBoolean()
    }

    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {
        return repository.forgetPwd(mobile, verifyCode)
            .convertBoolean()
    }

    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {
        return repository.resetPwd(mobile, pwd)
            .convertBoolean()
    }

    /**
     * 修改用户信息
     */
    override fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ): Observable<UserInfo> {
        return repository.editUser(
            userIcon,
            userName,
            userGender,
            userSign
        ).convert()
    }
}