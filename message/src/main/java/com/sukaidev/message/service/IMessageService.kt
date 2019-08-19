package com.sukaidev.message.service

import com.sukaidev.message.data.protocol.Message
import rx.Observable

/**
 * Created by sukaidev on 2019/08/19.
 * 消息业务接口.
 */
interface IMessageService {
    //获取消息列表
    fun getMessageList(): Observable<MutableList<Message>?>
}