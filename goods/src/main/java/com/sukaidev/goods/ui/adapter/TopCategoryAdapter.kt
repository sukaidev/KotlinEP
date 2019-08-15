package com.sukaidev.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.layout_top_category_item.view.*

/**
 * Created by sukaidev on 2019/08/15.
 * 一级商品分类Adapter.
 */
class TopCategoryAdapter(context: Context) :
    BaseRecyclerViewAdapter<Category, TopCategoryAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_top_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        // 分类名称
        holder.itemView.mTopCategoryNameTv.text = model.categoryName
        // 是否被选中
        holder.itemView.mTopCategoryNameTv.isSelected = model.isSelected
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}