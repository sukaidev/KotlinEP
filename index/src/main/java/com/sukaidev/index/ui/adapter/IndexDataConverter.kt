package com.sukaidev.index.ui.adapter

import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.sukaidev.core.ui.recycler.DataConverter
import com.sukaidev.core.ui.recycler.ItemType
import com.sukaidev.core.ui.recycler.MultipleFields
import com.sukaidev.core.ui.recycler.MultipleItemEntity

/**
 * Created by sukaidev on 2019/08/29.
 * 首页数据转换.
 */
class IndexDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataArray = JSON.parseObject(getJsonData()).getJSONArray("data")
        for (i in dataArray.withIndex()) {
            val data: JSONObject = dataArray.getJSONObject(i.index)
            val spanSize = data.getInteger("spanSize")
            val id = data.getInteger("goodsId")
            val describe = data.getString("describe")
            val type: Int
            val imageUrl: String
            val text: String
            val images = ArrayList<String>()
            when (describe) {
                "banner" -> {
                    type = ItemType.BANNER
                    val banners = data.getJSONArray("banners")
                    for (j in 0 until banners.size) {
                        images.add(banners.getString(j))
                    }
                    val entity: MultipleItemEntity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.BANNERS, images)
                        .setField(MultipleFields.SPAN_SIZE, spanSize)
                        .build()
                    ENTITIES.add(entity)
                }
                "news" -> {
                    type = ItemType.NEWS
                    val news = data.getJSONArray("news")
                    for (j in 0 until news.size) {
                        images.add(news.getString(j))
                    }
                    val entity: MultipleItemEntity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.NEWS, images)
                        .setField(MultipleFields.SPAN_SIZE, spanSize)
                        .build()
                    ENTITIES.add(entity)
                }
                "discount" -> {
                    type = ItemType.DISCOUNT
                    imageUrl = data.getString("imageUrl")
                    val discountBefore = data.getString("discountBefore")
                    val discountAfter = data.getString("discountAfter")
                    val entity: MultipleItemEntity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.SPAN_SIZE, spanSize)
                        .setField(MultipleFields.DISCOUNT_URL, imageUrl)
                        .setField(MultipleFields.DISCOUNT_BEFORE, discountBefore)
                        .setField(MultipleFields.DISCOUNT_AFTER, discountAfter)
                        .build()
                    ENTITIES.add(entity)
                }
                "goods" -> {
                    type = ItemType.GOODS
                    imageUrl = data.getString("imageUrl")
                    text = data.getString("text")
                    val entity: MultipleItemEntity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, type)
                        .setField(MultipleFields.ID, id)
                        .setField(MultipleFields.SPAN_SIZE, spanSize)
                        .setField(MultipleFields.IMAGE_URL, imageUrl)
                        .setField(MultipleFields.IMAGE_TEXT, text)
                        .build()
                    ENTITIES.add(entity)
                }
            }
        }
        return ENTITIES
    }
}