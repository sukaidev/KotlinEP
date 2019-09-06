package com.sukaidev.message.presenter.view

import com.sukaidev.core.presenter.view.BaseView
import com.sukaidev.message.data.protocol.Message

/**
 * Created by sukaidev on 2019/08/19.
 *
 */
interface MessageView : BaseView {

    //获取消息列表回调
    fun onGetMessageResult(result:MutableList<Message>?)
}