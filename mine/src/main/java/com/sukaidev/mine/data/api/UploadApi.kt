package com.sukaidev.mine.data.api

import com.sukaidev.core.data.protocol.BaseResp
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