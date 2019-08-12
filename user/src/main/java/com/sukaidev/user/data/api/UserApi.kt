package com.sukaidev.user.data.api

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.user.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/10.
 * 用户模块相关Api.
 */
interface UserApi {

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("userCenter/forgetPwd")
    fun forgetPwd(@Body req: ForgetPwdReq): Observable<BaseResp<String>>

    @POST("userCenter/resetPwd")
    fun resetPwd(@Body req: ResetPwdReq): Observable<BaseResp<String>>
}