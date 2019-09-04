package com.sukaidev.goods.ui.category

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sukaidev.core.ext.loadUrl
import com.sukaidev.goods.R
import com.sukaidev.goods.data.protocol.Category
import kotlinx.android.synthetic.main.item_category_content.view.*

/**
 * Created by sukaidev on 2019/09/01.
 *
 */
class CategoryContentAdapter(data: MutableList<Category>?) : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category_content, data) {

    override fun convert(holder: BaseViewHolder, item: Category?) {
        // 分类图片
        holder.itemView.mCategoryContentIconIv.loadUrl(item?.categoryIcon!!)
        // 分类名称
        holder.itemView.mCategoryContentNameTv.text = item.categoryName
    }
}
