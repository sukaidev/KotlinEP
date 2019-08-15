package com.sukaidev.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukaidev.common.ext.loadUrl
import com.sukaidev.common.ui.adapter.BaseRecyclerViewAdapter
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import kotlinx.android.synthetic.main.layout_second_category_item.view.*

/**
 * Created by sukaidev on 2019/08/15.
 * 二级分类适配器.
 */
class SecondCategoryAdapter(context: Context) :
    BaseRecyclerViewAdapter<Category, SecondCategoryAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_second_category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        // 分类图片
        holder.itemView.mCategoryIconIv.loadUrl(model.categoryIcon)
        // 分类名称
        holder.itemView.mSecondCategoryNameTv.text = model.categoryName
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}