package com.sukaidev.message.ui.fragment

import android.os.Bundle
import android.view.View
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.message.R
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.injection.component.DaggerMessageComponent
import com.sukaidev.message.injection.module.MessageModule
import com.sukaidev.message.presenter.MessagePresenter
import com.sukaidev.message.presenter.view.IMessageView

/**
 * Created by sukaidev on 2019/08/19.
 * 消息中心页面.
 */
class MessageFragment : BaseMvpFragment<MessagePresenter>(), IMessageView {

    override fun injectComponent() {
        DaggerMessageComponent
            .builder()
            .activityComponent(activityComponent)
            .messageModule(MessageModule())
            .build()
            .inject(this)
    }

    override fun setLayout(): Any {
        return R.layout.fragment_message
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetMessageResult(result: MutableList<Message>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}