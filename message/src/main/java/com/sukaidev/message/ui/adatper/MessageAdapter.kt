package com.sukaidev.message.ui.adatper

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.message.R
import com.sukaidev.message.data.protocol.Message
import kotlinx.android.synthetic.main.layout_message_item.view.*

/**
 * Created by sukaidev on 2019/08/19.
 * 消息页面数据适配.
 */
class MessageAdapter(data: MutableList<Message>?) : BaseQuickAdapter<Message, BaseViewHolder>(R.layout.layout_message_item, data) {


    override fun convert(holder: BaseViewHolder, item: Message?) {
        // 消息图标
        holder.itemView.mMsgIconIv.loadUrl(item?.msgIcon!!)
        // 消息标题
        holder.itemView.mMsgTitleTv.text = item.msgTitle
        // 消息内容
        holder.itemView.mMsgContentTv.text = item.msgContent
        // 消息时间
        holder.itemView.mMsgTimeTv.text = item.msgTime
    }

}