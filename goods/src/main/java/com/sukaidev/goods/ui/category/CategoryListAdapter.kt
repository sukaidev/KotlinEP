package com.sukaidev.goods.ui.category

import androidx.core.content.ContextCompat
import com.sukaidev.core.ui.recycler.*
import com.sukaidev.goods.R
import kotlinx.android.synthetic.main.item_category_list.view.*

/**
 * Created by sukaidev on 2019/08/31.
 * 分类列表数据适配.
 */
class CategoryListAdapter(data: MutableList<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {

    init {
        addItemType(ItemType.CATEGORY_LIST, R.layout.item_category_list)
    }

    companion object {
        fun create(data: MutableList<MultipleItemEntity>): CategoryListAdapter {
            return CategoryListAdapter(data)
        }

        fun create(converter: CategoryDataConverter): CategoryListAdapter {
            return CategoryListAdapter(converter.convert())
        }
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity?) {
        when (holder.itemViewType) {
            ItemType.CATEGORY_LIST -> {
                val isSelected = entity?.getField<Boolean>(MultipleFields.IS_SELECTED)
                // 分类名称
                holder.itemView.mCategoryListTv.text = entity?.getField(MultipleFields.NAME)
                // 是否被选中
                holder.itemView.mCategoryListTv.isSelected = isSelected!!
                if (isSelected) {
                    holder.itemView.view_line.setBackgroundColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.app_main
                        )
                    )
                } else {
                    holder.itemView.view_line.setBackgroundColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.colorTabText
                        )
                    )
                }
            }
        }
    }
}