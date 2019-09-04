package com.sukaidev.goods.ui.category

import com.sukaidev.core.ui.recycler.DataConverter
import com.sukaidev.core.ui.recycler.ItemType
import com.sukaidev.core.ui.recycler.MultipleFields
import com.sukaidev.core.ui.recycler.MultipleItemEntity
import com.sukaidev.goods.data.protocol.Category

/**
 * Created by sukaidev on 2019/08/31.
 * 将数据转换为MultipleItemEntity类型以支持BRVH.
 */
class CategoryDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val data = getListData()
        data?.forEach {
            val id = (it as Category).id
            val categoryName = it.categoryName
            val categoryIcon: String? = it.categoryIcon
            val parentId = it.parentId
            val isSelected = it.isSelected

            val itemType = if (parentId == 0) ItemType.CATEGORY_LIST else ItemType.CATEGORY_CONTENT

            val entity = MultipleItemEntity
                .builder()
                .setField(MultipleFields.ITEM_TYPE, itemType)
                .setField(MultipleFields.ID, id)
                .setField(MultipleFields.PARENT_ID, parentId)
                .setField(MultipleFields.NAME, categoryName)
                .setField(MultipleFields.IMAGE_URL, categoryIcon ?: "")
                .setField(MultipleFields.IS_SELECTED, isSelected)
                .setField(MultipleFields.SPAN_SIZE, if (parentId == 0) 0 else 1)
                .build()

            ENTITIES.add(entity)
        }
        return ENTITIES
    }
}