package com.sukaidev.message.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.sukaidev.core.ui.delegates.BaseMvpDelegate
import com.sukaidev.message.R
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.injection.module.MessageModule
import com.sukaidev.message.presenter.MessagePresenter
import com.sukaidev.message.presenter.view.IMessageView
import com.sukaidev.message.ui.adatper.MessageAdapter
import com.sukaidev.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.delegate_message.*

/**
 * Created by sukaidev on 2019/08/19.
 * 消息中心页面.
 */
class MessageDelegate : BaseMvpDelegate<MessagePresenter>(), IMessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun injectComponent() {
        DaggerMessageComponent
            .builder()
            .activityComponent(mActivityComponent)
            .messageModule(MessageModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.delegate_message
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mMessageRv.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageAdapter(null)
        mMessageRv.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mPresenter.getMessageList()
    }

    override fun onGetMessageResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0) {
            mAdapter.setNewData(result)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            Bus.send(MessageBadgeEvent(false))
        }
    }
}