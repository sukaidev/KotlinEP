package com.sukaidev.goods.data.protocol

/**
 * Created by sukaidev on 2019/08/15.
 * 商品SKU数据类.
 */
data class GoodsSku(
    val id: Int,
    val skuTitle: String,//SKU标题
    val skuContent: List<String>//SKU内容
)