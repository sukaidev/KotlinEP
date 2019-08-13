package com.sukaidev.user.service

import com.sukaidev.user.data.protocol.UserInfo
import rx.Observable

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
interface UserService {

    fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo>

    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>

    fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean>

    fun resetPwd(mobile: String, pwd: String): Observable<Boolean>

    fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ): Observable<UserInfo>
}