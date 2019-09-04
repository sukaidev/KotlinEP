package com.sukaidev.message.data.api

import com.sukaidev.core.data.protocol.BaseResp
import com.sukaidev.message.data.protocol.Message
import retrofit2.http.POST
import rx.Observable

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface MessageApi {

    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>
}