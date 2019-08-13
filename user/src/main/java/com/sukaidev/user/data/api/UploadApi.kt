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
interface UploadApi {

    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>
}