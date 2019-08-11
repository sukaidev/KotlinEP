package com.sukaidev.user.data.repository

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.user.data.api.UserApi
import com.sukaidev.user.data.protocol.RegisterReq
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukai on 2019/08/10.
 *
 */
class UserRepository @Inject constructor() {

    fun register(mobile: String, pwd: String, verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java).register(RegisterReq(mobile, pwd, verifyCode))
    }
}