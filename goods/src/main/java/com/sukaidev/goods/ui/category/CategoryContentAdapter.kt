package com.sukaidev.goods.ui.category

import com.sukaidev.core.ext.loadUrl
import com.sukaidev.core.ui.recycler.*
import com.sukaidev.goods.R
import kotlinx.android.synthetic.main.item_category_content.view.*

/**
 * Created by sukaidev on 2019/08/31.
 * 分类内容数据适配.
 */
class CategoryContentAdapter(data: MutableList<MultipleItemEntity>) :
    MultipleRecyclerAdapter(data) {
    init {
        addItemType(ItemType.CATEGORY_CONTENT, R.layout.item_category_content)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity?) {
        when (holder.itemViewType) {
            ItemType.CATEGORY_CONTENT -> {
                val imageUrl = entity?.getField<String>(MultipleFields.IMAGE_URL)
                // 分类图片
                holder.itemView.mCategoryContentIconIv.loadUrl(imageUrl!!)
                // 分类名称
                holder.itemView.mCategoryContentNameTv.text = entity.getField(MultipleFields.NAME)
            }
        }
    }
}