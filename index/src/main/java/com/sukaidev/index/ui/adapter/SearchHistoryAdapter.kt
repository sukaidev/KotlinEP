package com.sukaidev.index.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.index.R
import kotlinx.android.synthetic.main.layout_search_history_item.view.*

/**
 * Created by sukaidev on 2019/08/29.
 * 搜索历史页面数据适配器.
 */
class SearchHistoryAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_search_history_item) {

    override fun setOnItemClickListener(listener: OnItemClickListener?) {
        super.setOnItemClickListener(listener)
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.itemView.mSearchHistoryTv.text = item
    }
}