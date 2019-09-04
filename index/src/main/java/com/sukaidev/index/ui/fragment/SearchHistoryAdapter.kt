package com.sukaidev.index.ui.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.index.R
import kotlinx.android.synthetic.main.item_search_history.view.*

/**
 * Created by sukaidev on 2019/08/29.
 * 搜索历史页面数据适配器.
 */
class SearchHistoryAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_history) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.itemView.mSearchHistoryTv.text = item
    }
}