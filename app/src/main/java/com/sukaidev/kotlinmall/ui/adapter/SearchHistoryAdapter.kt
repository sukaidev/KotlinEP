package com.sukaidev.kotlinmall.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.kotlinmall.R
import kotlinx.android.synthetic.main.layout_search_history_item.view.*

/**
 * Created by sukaidev on 2019/08/15.
 * 搜索历史RecyclerView适配器.
 */
class SearchHistoryAdapter(context: Context) :
    BaseRecyclerViewAdapter<String, SearchHistoryAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(
                    R.layout.layout_search_history_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mSearchHistoryTv.text = model
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}