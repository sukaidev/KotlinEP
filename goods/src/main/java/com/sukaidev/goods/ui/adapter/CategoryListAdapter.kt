package com.sukaidev.goods.ui.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ui.recycler.*
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import kotlinx.android.synthetic.main.item_category_list.view.*

/**
 * Created by sukaidev on 2019/08/31.
 * 分类列表数据适配.
 */
class CategoryListAdapter(layoutId: Int, data: MutableList<Category>) : BaseQuickAdapter<Category, BaseViewHolder>(layoutId, data) {

    var mPrePosition: Int = 0

    override fun convert(holder: BaseViewHolder, item: Category?) {
        val isSelected = item?.isSelected
        // 分类名称
        holder.itemView.mCategoryListTv.text = item?.categoryName
        // 是否被选中
        holder.itemView.mCategoryListTv.isSelected = isSelected!!
        if (isSelected) {
            holder.itemView.view_line.visibility = View.VISIBLE
            holder.itemView.view_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main))
        } else {
            holder.itemView.view_line.visibility = View.INVISIBLE
        }
    }
}
