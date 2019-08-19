package com.sukaidev.message.data.repository

import com.sukaidev.common.data.net.RetrofitFactory
import com.sukaidev.common.data.protocol.BaseResp
import com.sukaidev.message.data.api.MessageApi
import com.sukaidev.message.data.protocol.Message
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 * 消息数据层.
 */
class MessageRepository @Inject constructor() {

    /**
     * 获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.instance.create(MessageApi::class.java).getMessageList()
    }
}