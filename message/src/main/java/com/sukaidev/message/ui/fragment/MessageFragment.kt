package com.sukaidev.message.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.sukaidev.common.ui.fragment.BaseMvpFragment
import com.sukaidev.message.R
import com.sukaidev.message.data.protocol.Message
import com.sukaidev.message.injection.component.DaggerMessageComponent
import com.sukaidev.message.injection.module.MessageModule
import com.sukaidev.message.presenter.MessagePresenter
import com.sukaidev.message.presenter.view.IMessageView
import com.sukaidev.message.ui.adatper.MessageAdapter
import com.sukaidev.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Created by sukaidev on 2019/08/19.
 * 消息中心页面.
 */
class MessageFragment : BaseMvpFragment<MessagePresenter>(), IMessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun injectComponent() {
        DaggerMessageComponent
            .builder()
            .activityComponent(activityComponent)
            .messageModule(MessageModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Any {
        return R.layout.fragment_message
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {
        mMessageRv.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageAdapter(context!!)
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
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.ViewState.CONTENT
        } else {
            //数据为空
            mMultiStateView.viewState = MultiStateView.ViewState.EMPTY
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            Bus.send(MessageBadgeEvent(false))
        }
    }
}