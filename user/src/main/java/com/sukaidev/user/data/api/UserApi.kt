package com.sukaidev.user.data.api

import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.user.data.protocol.RegisterReq
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukai on 2019/08/10.
 *
 */
interface UserApi {

    @POST("register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>
}