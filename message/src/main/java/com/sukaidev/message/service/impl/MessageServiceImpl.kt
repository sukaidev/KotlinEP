package com.sukaidev.message.service.impl

import com.sukaidev.core.ext.convert
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.data.repository.MessageRepository
import com.sukaidev.message.service.MessageService
import rx.Observable
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 * 消息业务接口实现.
 */
class MessageServiceImpl @Inject constructor() : MessageService {

    @Inject
    lateinit var repository: MessageRepository

    override fun getMessageList(): Observable<MutableList<Message>?> {
        return repository.getMessageList().convert()
    }
}