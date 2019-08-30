package com.sukaidev.user.data.repository

import com.sukaidev.core.data.net.RetrofitFactory
import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.user.data.api.UserApi
import com.sukaidev.user.data.protocol.*
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/10.
 *
 */
class UserRepository @Inject constructor() {

    fun login(mobile: String, pwd: String, pushId: String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .login(LoginReq(mobile, pwd, pushId))
    }


    fun register(mobile: String, pwd: String, verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, pwd, verifyCode))
    }

    /**
     * 忘记密码
     * @param mobile 帐号
     * @param verifyCode 验证码
     */
    fun forgetPwd(mobile: String, verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .forgetPwd(ForgetPwdReq(mobile, verifyCode))
    }

    /**
     * 重设密码
     * @param mobile 帐号
     * @param pwd 新密码
     */
    fun resetPwd(mobile: String, pwd: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .resetPwd(ResetPwdReq(mobile, pwd))
    }

    fun editUser(
        userIcon: String,
        userName: String,
        userGender: String,
        userSign: String
    ): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .editUser(EditUserReq(userIcon, userName, userGender, userSign))
    }
}