package com.sukaidev.message.presenter

import com.sukaidev.common.presenter.BasePresenter
import com.sukaidev.common.rx.BaseSubscriber
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.presenter.view.IMessageView
import javax.inject.Inject

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
class MessagePresenter @Inject constructor() : BasePresenter<IMessageView>() {

    @Inject
    lateinit var messageService: MessageService

    /*
        获取消息列表
     */
    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        messageService.getMessageList().excute(object : BaseSubscriber<MutableList<Message>?>(mView) {
            override fun onNext(t: MutableList<Message>?) {
                mView.onGetMessageResult(t)
            }
        }, lifecycleProvider)

    }
}