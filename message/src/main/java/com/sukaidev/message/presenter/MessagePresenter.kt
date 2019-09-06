package com.sukaidev.message.presenter

import com.sukaidev.core.ext.execute
import com.sukaidev.core.presenter.BasePresenter
import com.sukaidev.core.rx.BaseSubscriber
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.presenter.view.MessageView
import com.sukaidev.message.service.MessageService
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageService

    /*
        获取消息列表
     */
    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        messageService.getMessageList()
            .execute(object : BaseSubscriber<MutableList<Message>?>(mView) {
                override fun onNext(t: MutableList<Message>?) {
                    mView.onGetMessageResult(t)
                }
            }, lifecycleProvider)

    }
}